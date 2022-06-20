package matheus.paes.home.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import matheus.paes.features.github.datasource.IGithubRepoLocalDataSource
import matheus.paes.features.remoteKeys.datasource.IRemoteKeysLocalDataSource
import matheus.paes.home.data.mappers.toEntity
import matheus.paes.home.data.models.Repo
import matheus.paes.models.RemoteKeyEntity
import matheus.paes.models.RepoEntity
import matheus.paes.network.networkRequest.ResponseWrapper
import matheus.paes.network.networkRequest.doRequest

private const val GITHUB_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class GithubRemoteMediator(
    private val query: String,
    private val service: GithubApiService,
    private val githubRepoLocalDataSource: IGithubRepoLocalDataSource,
    private val remoteKeysLocalDataSource: IRemoteKeysLocalDataSource
) : RemoteMediator<Int, RepoEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepoEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> loadRefresh(state)
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        val apiQuery = query + IN_QUALIFIER

        return getNewPage(apiQuery, page, state, loadType)
    }

    private suspend fun loadRefresh(state: PagingState<Int, RepoEntity>): Int {
        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
        return remoteKeys?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RepoEntity>): RemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                remoteKeysLocalDataSource.remoteKeysRepoId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RepoEntity>): RemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                remoteKeysLocalDataSource.remoteKeysRepoId(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, RepoEntity>
    ): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                remoteKeysLocalDataSource.remoteKeysRepoId(repoId)
            }
        }
    }

    private suspend fun getNewPage(
        apiQuery: String,
        page: Int,
        state: PagingState<Int, RepoEntity>,
        loadType: LoadType
    ): MediatorResult {
        return try {
            when (val response = doRequest {
                service.getGithubRepositories(
                    apiQuery,
                    page,
                    state.config.pageSize
                )
            }) {
                is ResponseWrapper.Error -> MediatorResult.Error(response.exception)
                is ResponseWrapper.Success -> handleSuccess(response.result.items, page, loadType)
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun handleSuccess(items: List<Repo>, page: Int, loadType: LoadType): MediatorResult {
        val endOfPaginationReached = items.isEmpty()

        if (loadType == LoadType.REFRESH) {
            remoteKeysLocalDataSource.clearRemoteKeys()
            githubRepoLocalDataSource.clearRepos()
        }
        val prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page - 1
        val nextKey = if (endOfPaginationReached) null else page + 1
        val keys = items.map {
            RemoteKeyEntity(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
        }
        remoteKeysLocalDataSource.insertAll(keys)
        githubRepoLocalDataSource.insertAll(*items.map { it.toEntity() }.toTypedArray())

        return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
    }
}