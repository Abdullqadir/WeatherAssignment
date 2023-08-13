package com.example.weatherassignment.ui.screens.details_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherassignment.ui.shared.theme.Keyline24

@Composable
fun DetailsBody(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(Keyline24)
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.h4.copy(
                    color = MaterialTheme.colors.onSecondary.copy(alpha = 0.6f),
                )
            )
        }
        Box(modifier = Modifier.weight(1f)) {
            Text(
                text = value,
                style = MaterialTheme.typography.h3.copy(
                    color = MaterialTheme.colors.onSecondary,
                    textAlign = TextAlign.Start
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    DetailsBody(
        title = "Humidity",
        value = "10%"
    )
}
