package matheus.paes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import matheus.paes.features.github.dao.IGithubRepoDao
import matheus.paes.features.remoteKeys.dao.IRemoteKeysDao
import matheus.paes.models.*

@Database(entities = [RepoEntity::class, RemoteKeyEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    internal abstract fun githubRepoDao(): IGithubRepoDao
    internal abstract fun remoteKeysDao(): IRemoteKeysDao
}