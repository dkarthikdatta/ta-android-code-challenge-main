package com.example.mytripactions.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mytripactions.vm.NewsViewModel

@Composable
fun NewsNavGraph(newsViewModel: NewsViewModel) {
    val navController = rememberNavController()
    val newsUiState by newsViewModel.newsResult.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = "news_list") {
        composable("news_list") {
            NewsScreen(
                newsUiState = newsUiState,
                onLoadMore = { newsViewModel.loadMore() },
                onArticleClick = { article ->
                    newsViewModel.selectedArticle = article
                    navController.navigate("news_detail")
                })
        }
        composable("news_detail") {
            val article = newsViewModel.selectedArticle
            if (article != null) {
                NewsDescriptionScreen(
                    article = article, onBack = { navController.popBackStack() })
            }
        }
    }
}
