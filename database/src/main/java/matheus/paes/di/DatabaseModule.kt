package matheus.paes.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import matheus.paes.database.AppDatabase
import matheus.paes.features.github.datasource.GithubRepoLocalDataSourceImpl
import matheus.paes.features.github.datasource.IGithubRepoLocalDataSource
import matheus.paes.features.remoteKeys.datasource.IRemoteKeysLocalDataSource
import matheus.paes.features.remoteKeys.datasource.RemoteKeysLocalDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext ctx: Context): AppDatabase = Room.databaseBuilder(
        ctx,
        AppDatabase::class.java,
        "modularization-sample-app"
    ).build()

    @Provides
    fun provideGithubRepoDataSource(database: AppDatabase): IGithubRepoLocalDataSource {
        return GithubRepoLocalDataSourceImpl(database)
    }

    @Provides
    fun provideRemoteKeysDataSource(database: AppDatabase): IRemoteKeysLocalDataSource {
        return RemoteKeysLocalDataSourceImpl(database)
    }
}