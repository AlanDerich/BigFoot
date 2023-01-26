package com.derich.bigfoot.ui.screens.loans

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.derich.bigfoot.ui.common.CircularProgressBar
import com.derich.bigfoot.ui.screens.home.MemberDetails

@Composable
fun LoansComposable(modifier: Modifier = Modifier, loansViewModel: LoansViewModel, memberInfo: MemberDetails?) {
    //this screen contains details on loans history
//get data from firebase firestone
    val dataOrException = loansViewModel.data.value

    val loans = dataOrException.data

    loans?.let {
        LazyColumn{
            items(
                items = loans
            ){ loan ->
                LoansCard( loan = loan,
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
            isDisplayed = loansViewModel.loading.value
        )

    }
}

@Composable
fun LoansCard(loan: Loan,
                    modifier: Modifier
) {
    Column(horizontalAlignment = Alignment.Start,
        modifier = modifier
            .border(border = BorderStroke(width = 2.dp, color = Color.White))
            .padding(8.dp)
            .fillMaxWidth()) {
        Text(text = loan.username,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp))
        Spacer(modifier = Modifier.padding(2.dp))
        Text(text = "Date Loaned: ${ loan.dateLoaned }",
            modifier = Modifier.padding(start = 8.dp, end = 8.dp))
        Spacer(modifier = Modifier.padding(2.dp))
        Text(text = "Amount Loaned: KSH ${ loan.amountLoaned }",
            modifier = Modifier.padding(start = 8.dp, end = 8.dp))
        Spacer(modifier = Modifier.padding(2.dp))
        Text(text = "Transaction Charges: KSH ${ loan.transactionCharges }",
            modifier = Modifier.padding(start = 8.dp, end = 8.dp))
        Spacer(modifier = Modifier.padding(2.dp))
        if (loan.status){
            Text(text = "Status: Repaid",
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                color = Color.Green,
                fontWeight = FontWeight.Bold)
            Text(text = "Repaid Date: ${loan.dateRepaid}",
                modifier = Modifier.padding(start = 8.dp, end = 8.dp))
            Text(text = "Repaid Amount: ${loan.amountRepaid}",
                modifier = Modifier.padding(start = 8.dp, end = 8.dp))
        }
        else {
            Text(text = "Status: Not Paid",
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                color = Color.Red,
                fontWeight = FontWeight.Bold)
        }
    }
}
