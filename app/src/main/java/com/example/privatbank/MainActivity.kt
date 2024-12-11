package com.example.privatbank

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.privatbank.ui.theme.ExchangeRatesList
import com.example.privatbank.ui.theme.MainViewModel
import com.example.privatbank.utils.Resource
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(viewModel: MainViewModel) {
        val exchangeRates by viewModel.exchangeRates.collectAsStateWithLifecycle()
        var selectedDate by remember { mutableStateOf(dateFormat.format(Date())) }

        LaunchedEffect(selectedDate) {
            viewModel.fetchExchangeRates(selectedDate)
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Exchange Rates") },
                    actions = {
                        TextButton(onClick = { showDatePickerDialog { date -> selectedDate = date } }) {
                            Text(text = "Select Date")
                        }
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                when (val resource = exchangeRates) {
                    is Resource.Loading<*> -> {
                        CircularProgressIndicator()
                    }
                    is Resource.Success<*> -> {
                        if (resource.data.isNullOrEmpty()) {
                            Text("No exchange rates available.")
                        } else {
                            ExchangeRatesList(exchangeRates = resource.data)
                        }
                    }
                    is Resource.Error<*> -> {
                        Text(
                            text = resource.message ?: "Unknown error",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }

    private fun showDatePickerDialog(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = String.format(
                    Locale.getDefault(),
                    "%02d.%02d.%04d",
                    selectedDay,
                    selectedMonth + 1,
                    selectedYear
                )
                Toast.makeText(this, "Selected Date: $selectedDate", Toast.LENGTH_SHORT).show()
                onDateSelected(selectedDate)
            },
            year,
            month,
            day
        ).show()
    }
}