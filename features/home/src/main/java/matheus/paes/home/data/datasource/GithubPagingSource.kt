package matheus.paes.home.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import matheus.paes.features.github.datasource.IGithubRepoLocalDataSource
import matheus.paes.features.remoteKeys.datasource.IRemoteKeysLocalDataSource
import matheus.paes.home.data.mappers.toEntity
import matheus.paes.home.data.models.Repo
import matheus.paes.models.RepoEntity
import matheus.paes.network.networkRequest.ResponseWrapper
import matheus.paes.network.networkRequest.doRequest

class GithubPagingSource(
    private val query: String,
    private val service: GithubApiService
) : PagingSource<Int, RepoEntity>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoEntity> {
        val position = params.key ?: INITIAL_PAGE_INDEX

        return when (val response =
            doRequest { service.getGithubRepositories(query, position, params.loadSize) }) {
            is ResponseWrapper.Error -> LoadResult.Error(response.exception)
            is ResponseWrapper.Success -> return LoadResult.Page(
                data = response.result.items.toEntity(),
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (response.result.items.isEmpty()) null else position + 1
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RepoEntity>): Int? {
        return null
    }
}