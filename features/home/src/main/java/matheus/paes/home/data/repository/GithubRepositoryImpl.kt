package matheus.paes.home.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import matheus.paes.features.github.datasource.IGithubRepoLocalDataSource
import matheus.paes.features.remoteKeys.datasource.IRemoteKeysLocalDataSource
import matheus.paes.home.data.datasource.GithubApiService
import matheus.paes.home.data.datasource.GithubRemoteMediator
import matheus.paes.models.RepoEntity
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val service: GithubApiService,
    private val githubRepoLocalDataSource: IGithubRepoLocalDataSource,
    private val remoteKeysLocalDataSource: IRemoteKeysLocalDataSource
) : IGithubRepository {

    override fun getGithubRepositoriesStream(
        query: String
    ): Flow<PagingData<RepoEntity>> {
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { githubRepoLocalDataSource.reposByName(dbQuery) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = GithubRemoteMediator(
                query,
                service,
                githubRepoLocalDataSource,
                remoteKeysLocalDataSource
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }
}