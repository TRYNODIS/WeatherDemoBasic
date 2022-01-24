package hu.bme.aut.weatherdemo.util

sealed class NetworkResponse<out T : Any>

class NetworkResult<K : Any>(val result: K) : NetworkResponse<K>()

class NetworkErrorResult(val errorMessage: Exception) : NetworkResponse<Nothing>()
