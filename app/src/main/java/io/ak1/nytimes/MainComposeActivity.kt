package io.ak1.nytimes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ak1.nytimes.ui.home.StoriesViewModel
import io.ak1.nytimes.ui.screens.components.RootComponent
import org.koin.android.ext.android.inject


class MainComposeActivity : ComponentActivity() {
    private val liveViewModel by inject<StoriesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootComponent(liveViewModel, window)

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
            text = stringResource(id = R.string.app_name), modifier = Modifier
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