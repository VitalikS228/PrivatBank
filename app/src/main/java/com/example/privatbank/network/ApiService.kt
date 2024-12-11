package com.example.privatbank.network

import com.example.privatbank.model.ExchangeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("exchange_rates")
    suspend fun getExchangeRates(@Query("date") date: String): ExchangeResponse
}