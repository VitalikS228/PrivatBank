package com.example.privatbank.ui.theme

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.privatbank.model.ExchangeRate
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun ExchangeRatesList(exchangeRates: List<ExchangeRate>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(exchangeRates) { rate ->
            ExchangeRateItem(rate)
        }
    }
}

@Composable
fun ExchangeRateItem(rate: ExchangeRate) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Currency: ${rate.currency ?: "N/A"}",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Sale: ${rate.saleRate ?: "N/A"}",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Buy: ${rate.purchaseRate ?: "N/A"}",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
        )
    }
}
