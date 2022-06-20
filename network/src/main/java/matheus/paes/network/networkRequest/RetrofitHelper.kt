package matheus.paes.network.networkRequest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

private const val DEFAULT_ERROR_MESSAGE = "Não foi possível concluir a requisição"

suspend fun <T> doRequest(call: suspend () -> Response<T>): ResponseWrapper<T> {
    return withContext(Dispatchers.IO) {
        val response = call.invoke()

        try {
            return@withContext when {
                response.isSuccessful -> ResponseWrapper.Success(result = response.body()!!)
                response.code() in 400..499 -> ResponseWrapper.Error(Exception(DEFAULT_ERROR_MESSAGE))
                response.code() in 500..599 -> ResponseWrapper.Error(Exception(DEFAULT_ERROR_MESSAGE))
                else -> ResponseWrapper.Error(Exception(DEFAULT_ERROR_MESSAGE))
            }
        } catch (e: Exception) {
            return@withContext ResponseWrapper.Error(Exception(DEFAULT_ERROR_MESSAGE))
        }
    }
}