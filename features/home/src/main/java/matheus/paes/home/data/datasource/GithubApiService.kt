package matheus.paes.home.data.datasource

import matheus.paes.home.data.models.GithubRepositoriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/repositories?q=language:kotlin?sort=stars")
    suspend fun getGithubRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Response<GithubRepositoriesResponse>
}