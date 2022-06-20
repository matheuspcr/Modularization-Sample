package matheus.paes.features.github.datasource

import androidx.paging.PagingSource
import matheus.paes.models.RepoEntity

interface IGithubRepoLocalDataSource {
    suspend fun getAll(): List<RepoEntity>

    fun reposByName(queryString: String): PagingSource<Int, RepoEntity>

    suspend fun insertAll(vararg users: RepoEntity)

    suspend fun delete(user: RepoEntity)

    suspend fun clearRepos()
}