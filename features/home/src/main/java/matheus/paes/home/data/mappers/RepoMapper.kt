package matheus.paes.home.data.mappers

import matheus.paes.home.data.models.Repo
import matheus.paes.models.RepoEntity

fun Repo.toEntity() = RepoEntity(
    id = id,
    name = name,
    fullName = full_name,
    description = description,
    url = url,
    forks = forks_count,
    stars = stars,
    language = language,
    ownerName = owner.login,
    ownerPhoto = owner.avatar_url
)

fun List<Repo>.toEntity() = this.map { it.toEntity() }