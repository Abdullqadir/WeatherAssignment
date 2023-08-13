package com.example.weatherassignment.ui.screens.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherassignment.R
import com.example.weatherassignment.ui.shared.theme.Keyline16


@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    message: String? = "Error",
    action: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        message?.let { Text(text = it, modifier = Modifier.padding(Keyline16)) }
        Snackbar(
            modifier = Modifier
                .padding(Keyline16)
                .wrapContentHeight(Alignment.Bottom),
            action = {
                TextButton(onClick = action) {
                    Text(text = stringResource(R.string.retry))
                }
            }
        ) {
            Text(text = stringResource(R.string.retry_again))
        }
    }
}


@Preview
@Composable
fun ShowErrorScreen() {
    ErrorScreen(message = "Error") {}
}
