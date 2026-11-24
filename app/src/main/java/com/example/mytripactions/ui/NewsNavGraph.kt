package com.example.mytripactions.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mytripactions.vm.NewsViewModel

@Composable
fun NewsNavGraph(viewModel: NewsViewModel) {
    val navController = rememberNavController()
    val selectedArticle = viewModel.selectedArticle.collectAsStateWithLifecycle()
    val newsUiState = viewModel.newsResult.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = "news_list") {
        composable("news_list") {
            NewsScreen(
                newsUiState = newsUiState.value,
                onLoadMore = { viewModel.loadMore() },
                onArticleClick = { article ->
                    viewModel.selectArticle(article)
                    navController.navigate("description_page")
                })
        }


        composable("description_page") {
            selectedArticle.value?.let {
                NewsDescriptionScreen(article = it, onBack = { navController.popBackStack() })
            }
        }

    }

}