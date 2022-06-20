package matheus.paes.features.remoteKeys.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import matheus.paes.database.AppDatabase
import matheus.paes.models.RemoteKeyEntity

class RemoteKeysLocalDataSourceImpl(
    private val database: AppDatabase
): IRemoteKeysLocalDataSource {
    override suspend fun insertAll(remoteKey: List<RemoteKeyEntity>) = withContext(Dispatchers.IO) {
        database.remoteKeysDao().insertAll(remoteKey)
    }

    override suspend fun remoteKeysRepoId(repoId: Long): RemoteKeyEntity? = withContext(Dispatchers.IO) {
        return@withContext database.remoteKeysDao().remoteKeysRepoId(repoId)
    }

    override suspend fun clearRemoteKeys() = withContext(Dispatchers.IO) {
        database.remoteKeysDao().clearRemoteKeys()
    }
}