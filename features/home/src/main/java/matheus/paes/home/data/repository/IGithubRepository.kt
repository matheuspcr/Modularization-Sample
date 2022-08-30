package matheus.paes.home.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import matheus.paes.home.data.models.Repo
import matheus.paes.models.RepoEntity

interface IGithubRepository {

    fun getGithubRepositoriesStream(query: String): Flow<PagingData<RepoEntity>>
}