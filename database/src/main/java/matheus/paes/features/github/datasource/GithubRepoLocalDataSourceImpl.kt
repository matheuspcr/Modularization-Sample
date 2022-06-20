package matheus.paes.features.github.datasource

import androidx.paging.PagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import matheus.paes.database.AppDatabase
import matheus.paes.models.RepoEntity

class GithubRepoLocalDataSourceImpl(
    private val database: AppDatabase
) : IGithubRepoLocalDataSource {
    override suspend fun getAll(): List<RepoEntity> = withContext(Dispatchers.IO) {
        return@withContext database.githubRepoDao().getAll()
    }

    override fun reposByName(queryString: String): PagingSource<Int, RepoEntity> {
        return database.githubRepoDao().reposByName(queryString)
    }

    override suspend fun insertAll(vararg users: RepoEntity) = withContext(Dispatchers.IO) {
        database.githubRepoDao().insertAll(*users)
    }

    override suspend fun delete(user: RepoEntity) {
        database.githubRepoDao().delete(user)
    }

    override suspend fun clearRepos() = withContext(Dispatchers.IO) {
        database.githubRepoDao().clearRepos()
    }
}