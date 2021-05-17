package com.interview.thenewyorktimes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.interview.thenewyorktimes.ui.home.StoriesViewModel
import com.interview.thenewyorktimes.ui.screens.home.HomeScreenComposable
import com.interview.thenewyorktimes.ui.theme.TheNewYorkTimesAppTheme
import org.koin.android.ext.android.inject

internal val topBarTitle = mutableStateOf("Top bar ")

class MainComposeActivity : ComponentActivity() {
    private val liveViewModel by inject<StoriesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainComponent(liveViewModel)

        }
    }
}

fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val toolBarTitleId = createRefFor("ToolBarTitleId")
        val toolBarBookmarkId = createRefFor("ToolBarBookmarkId")
        val toolBarSettingsId = createRefFor("ToolBarSettingsId")


        constrain(toolBarTitleId) {
            start.linkTo(parent.start, margin = margin)
            end.linkTo(toolBarBookmarkId.start)
            top.linkTo(parent.top, margin = margin)
            bottom.linkTo(parent.bottom, margin = margin)
        }
        constrain(toolBarBookmarkId) {
            start.linkTo(toolBarTitleId.end)
            top.linkTo(parent.top, margin = margin)
            bottom.linkTo(parent.bottom, margin = margin)
            end.linkTo(toolBarSettingsId.start)
        }
        constrain(toolBarSettingsId) {
            start.linkTo(toolBarBookmarkId.end)
            top.linkTo(parent.top, margin = margin)
            bottom.linkTo(parent.bottom, margin = margin)
            end.linkTo(parent.end)
        }
    }
}


@Composable
fun MainComponent(liveViewModel: StoriesViewModel) {
    TheNewYorkTimesAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            val navController = rememberNavController().apply {
                topBarTitle.value = this.currentDestination?.label?.toString() ?: ""
            }


            Scaffold(
                // TODO: 15/05/21 not working on back-pressed
                topBar = {
                    TopAppBar(
                        modifier = Modifier
                            .fillMaxWidth()


                    ) {
                        Text(
                            text = topBarTitle.value,
                            modifier = Modifier
                                .width(100.dp)
                                .padding(16.dp, 0.dp, 0.dp, 0.dp)
                                .layoutId("ToolBarTitleId")
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_bookmark),
                                contentDescription = "hie",
                                //colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                                modifier = Modifier
                                    .padding(12.dp)
                                    .layoutId("ToolBarBookmarkId")
                            )
                            Image(
                                painter = painterResource(R.drawable.ic_settings),
                                contentDescription = "hie",
                                modifier = Modifier
                                    .padding(12.dp)
                                    .layoutId("ToolBarSettingsId")
                            )
                        }


                        /* ConstraintLayout(constraintSet = decoupledConstraints(5.dp)) {
                             Text(
                                 text = topBarTitle.value,
                                 modifier = Modifier.width(0.dp)
                                     .layoutId("ToolBarTitleId")
                             )
                             Image(
                                 painter = painterResource(R.drawable.ic_bookmark),
                                 contentDescription = "hie",
                                 //colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                                 modifier = Modifier
                                     .padding(12.dp)
                                     .layoutId("ToolBarBookmarkId")
                             )
                             Image(
                                 painter = painterResource(R.drawable.ic_settings),
                                 contentDescription = "hie",
                                 modifier = Modifier
                                     .padding(12.dp)
                                     .layoutId("ToolBarSettingsId")
                             )
                         }*/


                    }

                }
            ) {
                topBarTitle.value = "Home"
                NavHost(
                    navController = navController,
                    startDestination = "eng",
                    modifier = Modifier.padding(16.dp, 0.dp)
                ) {
                    composable("eng") {
                        HomeScreenComposable(liveViewModel, navController) {
                            topBarTitle.value = it
                        }
                    }
                    composable("kor") {
                        Greeting2("kim", navController) {
                            topBarTitle.value = it
                        }
                    }
                }
            }


        }
    }
}


@Composable
fun Greeting2(name: String, navController: NavController, callback: (String) -> Unit) {
    Column {

        Text(text = "annyeonghaseyo $name!")
        Button(onClick = {
            callback("English top")
            navController.navigate("eng") {
                popUpTo = navController.graph.startDestination
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true

            }
        }) {
            Text(text = "move to english")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            text = topBarTitle.value, modifier = Modifier
                .fillMaxWidth()

        )
        Image(
            painter = painterResource(R.drawable.ic_bookmark),
            contentDescription = "hie"
        )
        Image(
            painter = painterResource(R.drawable.ic_settings),
            contentDescription = "hie"
        )

    }
}