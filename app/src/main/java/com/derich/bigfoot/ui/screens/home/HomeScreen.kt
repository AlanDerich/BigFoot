package com.derich.bigfoot.ui.screens.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.derich.bigfoot.R
import com.derich.bigfoot.ui.common.CircularProgressBar
import kotlinx.coroutines.ExperimentalCoroutinesApi


//this is the default home screen
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun HomeComposable(modifier: Modifier = Modifier,
                   viewModel: ContributionsViewModel,
                   memberInfo: MemberDetails?
) {
    val contributions = viewModel.data.value.data
    val context = LocalContext.current
    var displayMemberInfo by remember { mutableStateOf(false) }
    var memberContributions by remember { mutableStateOf(Contributions()) }
    if(memberInfo != null){
//        val memberCont = contributions!!.contains("", )
        if (displayMemberInfo){
            Column {
                Row {
                    val differenceInContributions = viewModel.calculateContributionsDifference(
                        memberContributions.totalAmount?.toInt() ?: 0
                    )
                    if( differenceInContributions < 0){
                        Icon(painter = painterResource(id = R.drawable.baseline_check_circle_24),
                            contentDescription = "Status of Contribution",
                            modifier = Modifier.size(68.dp))
                        Text(text = "Hello ${memberInfo.firstName}, you\'re on ${memberContributions.date}. Congrats! You are KSH $differenceInContributions ahead on schedule")
                    }
                    else{
                        Icon(painter = painterResource(id = R.drawable.baseline_cancel_24),
                            contentDescription = "Status of Contribution",
                            modifier = Modifier.size(68.dp))
                        Text(text = "Hello ${memberInfo.firstName}, you\'re on ${memberContributions.date}. You need KSH $differenceInContributions to be back on track.")
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                }
                contributions?.let {
                    LazyColumn(modifier = modifier.fillMaxSize()) {
                        items(
                            items = contributions
                        ) { contribution ->
                            ContributionCard(contribution = contribution,
                                modifier = modifier)
                        }
                    }
                }
            }
        }
        else{
            contributions?.forEach {contribution ->
                if (contribution.Name == (memberInfo.firstName + " " + memberInfo.secondName + " " + memberInfo.surname)){
                    memberContributions = contribution
                    displayMemberInfo = true

                }
            }
        }



        val e = viewModel.data.value.e
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
                isDisplayed = viewModel.loadingContributions.value || viewModel.loadingMemberDetails.value
            )

        }
        BackHandler {
            val activity = (context as? Activity)
            activity?.finish()
        }
    }
    else {
        Text(text = "Oops. Your details we're not found in our database")
    }


}

@Composable
fun ContributionCard(contribution: Contributions,
                     modifier: Modifier
) {
    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://firebasestorage.googleapis.com/v0/b/bigfut-bc86a.appspot.com/o/profilepics%2FIMG_20180729_115034.jpg?alt=media&token=37b5420c-5caa-4a6b-9883-12639d2a5191"),
            contentDescription = stringResource(R.string.profile_image_description),
            Modifier
                .clip(MaterialTheme.shapes.medium)
                .size(68.dp)
        )
        UsersColumn(contribution = contribution)
    }
}

@Composable 
fun UsersColumn(modifier: Modifier = Modifier, contribution: Contributions) {
        Column(horizontalAlignment = Alignment.Start, modifier = modifier.padding(8.dp)) {
            Text(text = contribution.Name!!, fontWeight = Bold)
            Spacer(modifier = Modifier.padding(2.dp))
            Text(text = "KSH ${contribution.totalAmount!!}")
            Spacer(modifier = Modifier.padding(2.dp))
            Text(text = contribution.date!!) 
        } 
}
