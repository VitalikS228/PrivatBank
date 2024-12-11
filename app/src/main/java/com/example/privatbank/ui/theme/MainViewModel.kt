package com.example.privatbank.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.privatbank.model.ExchangeRate
import com.example.privatbank.repository.ExchangeRepository
import com.example.privatbank.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = ExchangeRepository()

    private val _exchangeRates = MutableStateFlow<Resource<List<ExchangeRate>>>(Resource.Loading())
    val exchangeRates: StateFlow<Resource<List<ExchangeRate>>> = _exchangeRates.asStateFlow()

    fun fetchExchangeRates(date: String) {
        _exchangeRates.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val response = repository.getExchangeRates(date)
                val rates = response.exchangeRate
                if (!rates.isNullOrEmpty()) {
                    _exchangeRates.value = Resource.Success(rates)
                } else {
                    _exchangeRates.value = Resource.Error("No exchange rates available.")
                }
            } catch (e: Exception) {
                _exchangeRates.value = Resource.Error("Error: ${e.message}")
            }
        }
    }
}
