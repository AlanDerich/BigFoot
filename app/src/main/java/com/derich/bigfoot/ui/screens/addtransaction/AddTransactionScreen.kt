package com.derich.bigfoot.ui.screens.addtransaction

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.derich.bigfoot.ui.screens.home.MemberDetails
import com.derich.bigfoot.ui.screens.transactions.TransactionsViewModel
import java.util.*

@Composable
fun AddTransactionScreen(transactionsViewModel: TransactionsViewModel,
                         allMemberInfo: List<MemberDetails>, modifier: Modifier = Modifier) {
//    var requestToOpen by remember { mutableStateOf(false) }
    Column(modifier = modifier.padding(8.dp)) {
        var selectedMember by remember { mutableStateOf(allMemberInfo[0]) }
        val isOpen = remember { mutableStateOf(false) } // initial value
        val openCloseOfDropDownList: (Boolean) -> Unit = {
            isOpen.value = it
        }
        val userSelectedString: (MemberDetails) -> Unit = {
            selectedMember = it
        }
        Box {
            Column {
                OutlinedTextField(
                    value = selectedMember.fullNames,
                    onValueChange = { selectedMember.fullNames = it },
                    label = { Text(text = "TextFieldTitle") },
                    modifier = Modifier.wrapContentWidth()
                )
                DropDownList(
                    requestToOpen = isOpen.value,
                    list = allMemberInfo,
                    openCloseOfDropDownList,
                    userSelectedString
                )
            }
            Spacer(
                modifier = Modifier.matchParentSize().background(Color.Transparent).padding(10.dp)
                    .clickable(
                        onClick = { isOpen.value = true }
                    )
            )
        }

        AddTransactionPage(selectedMember, transactionsViewModel)
    }

}

@Composable
fun AddTransactionPage(selectedMember: MemberDetails,
                       transactionsViewModel: TransactionsViewModel) {
    var dateOfTransaction by remember { mutableStateOf("01/01/2020") }
    var transactionPaidBy by remember { mutableStateOf("") }
    var transactionAmountPaid by remember { mutableStateOf("0") }
    var transactionConfirmation by remember { mutableStateOf("") }
    OutlinedTextField(
        label = { Text(text = "Date of Transaction") },
        value = dateOfTransaction,
        onValueChange = {dateOfTransaction = it},
        modifier = Modifier.padding(top = 4.dp))
    OutlinedTextField(
        label = { Text(text = "Paid by") },
        value = transactionPaidBy,
        onValueChange = {transactionPaidBy = it},
        modifier = Modifier.padding(top = 4.dp))
    OutlinedTextField(value = transactionAmountPaid,
        onValueChange = { transactionAmountPaid = it },
        label = { Text(text = "Amount paid") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.padding(top = 4.dp)
        )
    OutlinedTextField(
        label = { Text(text = "Confirmation Message") },
        value = transactionConfirmation,
        onValueChange = {transactionConfirmation = it},
        modifier = Modifier.padding(top = 4.dp))
    OutlinedTextField(
        label = { Text(text = "Previous amount") },
        value = selectedMember.totalAmount,
        enabled = false,
        onValueChange = {},
        modifier = Modifier.padding(top = 4.dp))
        Button(
            onClick = { transactionsViewModel.addTransaction() },
            modifier = Modifier.padding(top = 4.dp))
        {
            Text(text = "Add Transaction")
        }
}

@Composable
fun DropDownList(
    requestToOpen: Boolean = false,
    list: List<MemberDetails>,
    request: (Boolean) -> Unit,
    selectedString: (MemberDetails) -> Unit
) {
    DropdownMenu(
        modifier = Modifier.fillMaxWidth(),
        expanded = requestToOpen,
        onDismissRequest = { request(false) },
    ) {
        list.forEach {
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    request(false)
                    selectedString(it)
                }
            ) {
                Text(it.fullNames, modifier = Modifier.wrapContentWidth())
            }
        }
    }
}
@Composable
fun MyDatePicker(): String{

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )
    return mDate.value
}