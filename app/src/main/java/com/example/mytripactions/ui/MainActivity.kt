package com.example.mytripactions.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mytripactions.compose.TATheme
import com.example.mytripactions.vm.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TATheme {
                val newsUiState by newsViewModel.newsResult.collectAsStateWithLifecycle()
                NewsScreen(
                    newsUiState = newsUiState,
                    onLoadMore = { newsViewModel.loadMore() }
                )
            }
        }
    }
}
