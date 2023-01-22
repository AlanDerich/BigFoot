package com.derich.bigfoot.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.derich.bigfoot.R

@Composable
fun EnterPhoneNumberUI(
    modifier: Modifier = Modifier
        .padding(vertical = 56.dp, horizontal = 24.dp),
    onClick: () -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    onDone: (KeyboardActionScope.() -> Unit)?
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painterResource(id = R.drawable.bigfut1),
            contentDescription = "Bigfoot Icon",
            modifier = Modifier.padding(8.dp)
                .clip(MaterialTheme.shapes.small)
                .size(104.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.phone_number_text))

        Spacer(modifier = Modifier.height(20.dp))
        PhoneNumberTextField(
            phone = phone,
            onNumberChange = onPhoneChange,
            onDone = onDone
        )
        Button(
            onClick,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.next))
        }

    }
}


@Composable
fun PhoneNumberTextField(
    phone: String,
    onNumberChange: (String) -> Unit,
    onDone: (KeyboardActionScope.() -> Unit)?
) {
    OutlinedTextField(
        value = phone, onValueChange = onNumberChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = onDone),
        singleLine = true,
        leadingIcon = {
            Icon(Icons.Default.Phone, contentDescription = "")
        },
        placeholder = { Text(text = "e.g +254712345678") },
        isError = phone.isEmpty()
        )
}
fun validate(text: String) {

}