package com.interview.thenewyorktimes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.interview.thenewyorktimes.ui.theme.TheNewYorkTimesAppTheme

class MainComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheNewYorkTimesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()

                    val title = mutableStateOf("Top bar ")

                    Scaffold(
                        // TODO: 15/05/21 not working on back-pressed
                        topBar = { Text(text = title.value, modifier = Modifier.padding(20.dp)) }
                    ) {
                        NavHost(navController = navController, startDestination = "eng") {
                            composable("eng") {
                                Greeting("jon", navController) {
                                    title.value = it
                                }
                            }
                            composable("kor") {
                                Greeting2("kim", navController) {
                                    title.value = it
                                }
                            }
                        }
                    }


                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, navController: NavController, callback: (String) -> Unit) {
    Column {
        Button(onClick = {
            callback("korean top")
            navController.navigate("kor")
        }) {
            Text(text = "move to korean")
        }

        Text(text = "Hello $name!")
    }
}

@Composable
fun Greeting2(name: String, navController: NavController, callback: (String) -> Unit) {
    Column {

        Text(text = "annyeonghaseyo $name!")
        Button(onClick = {
            callback("English top")
            navController.navigate("eng") {

            }
        }) {
            Text(text = "move to english")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TheNewYorkTimesAppTheme {

    }
}