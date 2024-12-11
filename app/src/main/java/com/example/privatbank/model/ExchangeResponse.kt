package com.example.privatbank.model

data class ExchangeResponse(
    val date: String?,
    val bank: String?,
    val baseCurrency: Int?,
    val baseCurrencyLit: String?,
    val exchangeRate: List<ExchangeRate>?
)