package matheus.paes.home.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import matheus.paes.features.github.datasource.IGithubRepoLocalDataSource
import matheus.paes.features.remoteKeys.datasource.IRemoteKeysLocalDataSource
import matheus.paes.home.data.datasource.GithubApiService
import matheus.paes.home.data.repository.GithubRepositoryImpl
import matheus.paes.home.data.repository.IGithubRepository
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    fun provideService(retrofit: Retrofit): GithubApiService {
        return retrofit.create(GithubApiService::class.java)
    }

    @Provides
    fun provideGithubRepository(
        service: GithubApiService,
        githubRepoLocalDataSource: IGithubRepoLocalDataSource,
        remoteKeysLocalDataSource: IRemoteKeysLocalDataSource
    ): IGithubRepository {
        return GithubRepositoryImpl(service)
    }

}