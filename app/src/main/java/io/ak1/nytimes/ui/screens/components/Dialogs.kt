package io.ak1.nytimes.ui.screens.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.ak1.nytimes.R


@Composable
fun CustomAlertDialog(
    @StringRes titleId: Int = R.string.cache_summary,
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit,
    callback: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { setShowDialog(false) }, title = {
                Text(
                    text = stringResource(
                        id = titleId
                    ),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            },
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = {
                            setShowDialog(false)
                            callback()
                        },
                        modifier = Modifier.padding(8.dp, 0.dp)
                    ) {
                        Text(
                            text = stringResource(
                                id = android.R.string.ok
                            ),
                            style = MaterialTheme.typography.caption
                        )
                    }
                    Button(
                        onClick = { setShowDialog(false) },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = stringResource(
                                id = android.R.string.cancel
                            ),
                            style = MaterialTheme.typography.caption
                        )
                    }
                }

            }
        )
    }
}