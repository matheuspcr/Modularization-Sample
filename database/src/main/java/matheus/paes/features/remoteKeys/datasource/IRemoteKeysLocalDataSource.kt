package matheus.paes.features.remoteKeys.datasource

import matheus.paes.models.RemoteKeyEntity

interface IRemoteKeysLocalDataSource {

    suspend fun insertAll(remoteKey: List<RemoteKeyEntity>)

    suspend fun remoteKeysRepoId(repoId: Long): RemoteKeyEntity?

    suspend fun clearRemoteKeys()
}