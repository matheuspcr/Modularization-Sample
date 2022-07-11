package matheus.paes.helper

sealed class ViewState<out T> {
    data class Success<T>(val data: T): ViewState<T>()
    data class Error(val exception: Exception): ViewState<Nothing>()
    object Loading: ViewState<Nothing>()
}