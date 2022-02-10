package com.example.testhiltmvvm.data.remote


import android.util.Log
import com.example.testhiltmvvm.utils.DataResource
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): DataResource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return DataResource.success(body)
            }
            return error(" ${response.code()} ${response.message()}",)
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): DataResource<T> {
        return DataResource.error("Network call has failed for a following reason: $message")
    }

    private fun <T> error(message: String, data: T): DataResource<T> {
        Log.e("TAG", "error data: $data" )
        return DataResource.error("Network call has failed for a following reason: $message")
    }

}