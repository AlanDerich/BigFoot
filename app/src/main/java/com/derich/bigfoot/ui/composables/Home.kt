package com.derich.bigfoot.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.derich.bigfoot.R
import com.derich.bigfoot.ui.data.Contributions
import com.derich.bigfoot.ui.data.ContributionsViewModel
import com.derich.bigfoot.ui.data.DataOrException
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun HomeComposable(modifier: Modifier = Modifier.fillMaxSize(),
                   dataOrException: DataOrException<List<Contributions>, Exception>,
viewModel: ContributionsViewModel) {
    val contributions = dataOrException.data
    contributions?.let {
        LazyColumn {
            items(
                items = contributions
            ) { contribution ->
                ContributionCard(contribution = contribution,
                    modifier = modifier
                    ,viewModel = viewModel)
            }
        }
    }

    val e = dataOrException.e
    e?.let {
        Text(
            text = e.message!!,
            modifier = Modifier.padding(16.dp)
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressBar(
            isDisplayed = viewModel.loading.value
        )

    }
}

@Composable
fun ContributionCard(contribution: Contributions,
                     modifier: Modifier,
                     viewModel: ContributionsViewModel) {
    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = R.drawable.bigfut1),
            contentDescription = "User profile image",
            Modifier
                .clip(MaterialTheme.shapes.medium)
                .size(64.dp)
        )
        UsersColumn(contribution = contribution)
    }
}

    @Composable
    fun UsersColumn(modifier: Modifier = Modifier.padding(8.dp), contribution: Contributions) {
        Column(horizontalAlignment = Alignment.Start, modifier = modifier) {
            Text(text = contribution.Name!!)
            Spacer(modifier = Modifier.padding(2.dp))
            Text(text = contribution.totalAmount!!)
            Spacer(modifier = Modifier.padding(2.dp))
            Text(text = contribution.date!!)
        }
    }

@ExperimentalCoroutinesApi
@Composable
fun CircularProgressBar(
    isDisplayed: Boolean
) {
    if (isDisplayed) {
        CircularProgressIndicator()
    }
}