package matheus.paes.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey val repoId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)