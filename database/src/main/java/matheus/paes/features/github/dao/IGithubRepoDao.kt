package matheus.paes.features.github.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import matheus.paes.models.RepoEntity

@Dao
internal interface IGithubRepoDao {
    @Query("SELECT * FROM github_repositories")
    suspend fun getAll(): List<RepoEntity>

    @Query(
        "SELECT * FROM github_repositories WHERE " +
                "name LIKE :queryString OR description LIKE :queryString " +
                "ORDER BY stars DESC, name ASC"
    )
    fun reposByName(queryString: String): PagingSource<Int, RepoEntity>

    @Insert
    suspend fun insertAll(vararg repos: RepoEntity)

    @Delete
    suspend fun delete(repo: RepoEntity)

    @Query("DELETE FROM github_repositories")
    suspend fun clearRepos()
}