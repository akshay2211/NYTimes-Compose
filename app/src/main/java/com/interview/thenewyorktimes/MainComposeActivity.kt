package com.interview.thenewyorktimes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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

                    NavHost(navController = navController, startDestination = "eng") {
                        composable("eng") { Greeting("jon", navController) }
                        composable("kor") { Greeting2("kim", navController) }
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, navController: NavController) {
    Column {
        Button(onClick = { navController.navigate("kor") }) {
            Text(text = "move to korean")
        }

        Text(text = "Hello $name!")
    }
}

@Composable
fun Greeting2(name: String, navController: NavController) {
    Column {

        Text(text = "annyeonghaseyo $name!")
        Button(onClick = {
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