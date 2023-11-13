@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.moviesapp.ui.components.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviesapp.R

@Composable
fun MovieErrorPage(errorType: MovieErrorType, modifier: Modifier = Modifier, onRetry: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(stringResource(R.string.app_bar_error_title)) })
    }) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            val text = when (errorType) {
                MovieErrorType.NETWORK_ERROR -> stringResource(R.string.network_error_text)
                MovieErrorType.SERVER_ERROR -> stringResource(R.string.server_error_text)
                MovieErrorType.NOT_FOUND -> stringResource(R.string.not_found_error_text)
                MovieErrorType.UNKNOWN -> stringResource(R.string.unknown_error_text)
            }
            Icon(
                Icons.Rounded.Warning,
                tint = MaterialTheme.colorScheme.error,
                contentDescription = stringResource(R.string.error_icon_description)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text, modifier = modifier)
            Spacer(modifier = Modifier.height(16.dp))
            ElevatedButton(onClick = onRetry) {
                Text(text = stringResource(R.string.retry_btn))
            }
        }
    }
}

enum class MovieErrorType {
    NETWORK_ERROR,
    SERVER_ERROR,
    NOT_FOUND,
    UNKNOWN
}