package com.example.privatbank.repository

import com.example.privatbank.model.ExchangeResponse
import com.example.privatbank.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExchangeRepository {
    suspend fun getExchangeRates(date: String): ExchangeResponse {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.api.getExchangeRates(date)
        }
    }
}