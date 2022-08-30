package matheus.paes.home.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import matheus.paes.features.github.datasource.IGithubRepoLocalDataSource
import matheus.paes.features.remoteKeys.datasource.IRemoteKeysLocalDataSource
import matheus.paes.home.data.datasource.GithubApiService
import matheus.paes.home.data.datasource.GithubPagingSource
import matheus.paes.home.data.models.Repo
import matheus.paes.models.RepoEntity
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val service: GithubApiService
) : IGithubRepository {

    override fun getGithubRepositoriesStream(
        query: String
    ): Flow<PagingData<RepoEntity>> {

        return Pager(PagingConfig(NETWORK_PAGE_SIZE)) { GithubPagingSource(query, service) }.flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}