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
    val selectedArticle by newsViewModel.selectedArticle.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = "news_list") {
        composable("news_list") {
            NewsScreen(
                newsUiState = newsUiState,
                onLoadMore = { newsViewModel.loadMore() },
                onArticleClick = { article ->
                    newsViewModel.selectArticle(article)
                    navController.navigate("news_detail")
                }
            )
        }
        composable("news_detail") {
            selectedArticle?.let { article ->
                NewsDescriptionScreen(
                    article = article,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
