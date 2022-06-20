package matheus.paes.network.networkRequest

import java.lang.Exception

sealed interface ResponseWrapper<out T> {
    data class Error(val exception: Exception): ResponseWrapper<Nothing>
    data class Success<T>(val result: T): ResponseWrapper<T>
}