package com.example.weatherassignment.ui.shared.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherassignment.R
import com.example.weatherassignment.ui.shared.theme.Keyline0


@Composable
fun TopAppBarComposable(
    title: String,
    action: @Composable() (RowScope.() -> Unit),
    navigationIcon: @Composable() (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Text(
                text = title, modifier = Modifier.wrapContentWidth(Alignment.Start)
            )
        },
        actions = action,
        navigationIcon = navigationIcon,
        elevation = Keyline0,
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = MaterialTheme.colors.onSecondary
    )
}


@Preview
@Composable
fun TopAppBarComposablePreview() {
    TopAppBarComposable(
        title = stringResource(R.string.weather_screen_title),
        action = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null
                )
            }
        },
    )
}