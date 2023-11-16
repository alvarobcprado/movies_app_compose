@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.moviesapp.ui.components.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviesapp.core.LocalNavController
import com.example.moviesapp.core.MoviesAppRoutingPreview
import com.example.moviesapp.core.canGoBack

@Composable
fun MoviesTopBar(title: String) {
    val navController = LocalNavController.current
    val canGoBack = navController.canGoBack()

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon =
            if(canGoBack) {
                {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            }else {{}}
    )
}

@Preview
@Composable
fun MoviesTopBarPreview() {
    MoviesAppRoutingPreview {
        MoviesTopBar(title = "Title")
    }
}

