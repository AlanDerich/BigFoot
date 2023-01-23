package com.derich.bigfoot.ui.screens.transactions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.derich.bigfoot.ui.common.CircularProgressBar
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun TransactionsComposable(modifier: Modifier = Modifier,
                           transactionsViewModel: TransactionsViewModel) {

    //get data from firebase firestone
    val dataOrException = transactionsViewModel.data.value

    val transactions = dataOrException.data

    transactions?.let {
        LazyColumn{
            items(
                items = transactions
            ){ transaction ->
                TransactionCard( transaction = transaction,
                modifier = modifier)
            }
        }
    }
    val e = dataOrException.e
    e?.let {
        Text(text = e.message!!,
            modifier = modifier.padding(16.dp)
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressBar(
            isDisplayed = transactionsViewModel.loading.value
        )

    }
}

@Composable
fun TransactionCard(transaction: Transactions,
                    modifier: Modifier
) {
        Column(horizontalAlignment = Alignment.Start,
            modifier = modifier
                .border(border = BorderStroke(width = 2.dp, color = Color.White))
                .padding(8.dp)
                .fillMaxWidth()) {
            Text(text = transaction.depositFor!!,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp))
            Spacer(modifier = Modifier.padding(2.dp))
            Text(text = "Date: ${ transaction.transactionDate!! }",
                modifier = Modifier.padding(start = 8.dp, end = 8.dp))
            Spacer(modifier = Modifier.padding(2.dp))
            Text(text = "Amount: KSH ${ transaction.transactionAmount }",
                modifier = Modifier.padding(start = 8.dp, end = 8.dp))
            Spacer(modifier = Modifier.padding(2.dp))
            Text(text = transaction.transactionConfirmation!!,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp))
        }
}
