package matheus.paes.home.presentation.ui.home


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import matheus.paes.helper.ViewState
import matheus.paes.home.data.repository.IGithubRepository
import matheus.paes.models.RepoEntity
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: IGithubRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var lastQuery: String = ""

    val accept: (UiAction) -> Unit = { action ->
        val query = when (action) {
            UiAction.Refresh -> lastQuery
            is UiAction.Search -> action.query
        }
        viewModelScope.launch { searchQuery.emit(query) }
    }

    private val searchQuery = MutableSharedFlow<String>()

    val data: Flow<PagingData<RepoEntity>> = searchQuery.flatMapLatest { query ->
        repository.getGithubRepositoriesStream(query)
    }.cachedIn(viewModelScope)

}

sealed class UiAction {
    data class Search(val query: String): UiAction()
    object Refresh: UiAction()
}
