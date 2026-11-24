package com.example.mytripactions.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytripactions.api.NewsArticleDto
import com.example.mytripactions.api.NewsResponse
import com.example.mytripactions.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface NewsUiState {
    data object Loading : NewsUiState
    data class Success(val data: NewsResponse) : NewsUiState
    data class Error(val message: String) : NewsUiState
}

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    private var currentPage = 1
    private var isFetching = false
    private val allArticles = mutableListOf<NewsArticleDto>()

    private val _selectedArticle = MutableStateFlow<NewsArticleDto?>(null)
    val selectedArticle: StateFlow<NewsArticleDto?> = _selectedArticle.asStateFlow()

    fun selectArticle(article: NewsArticleDto) {
        _selectedArticle.value = article
    }

    private val _newsResult = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val newsResult: StateFlow<NewsUiState> = _newsResult.asStateFlow()

    init {
        loadNews()
    }

    fun loadNews() {
        if (isFetching) return
        isFetching = true
        currentPage = 1
        viewModelScope.launch {
//            if (allArticles.isEmpty()) {
//                _newsResult.value = NewsUiState.Loading
//            }
//            newsRepository.getNews(page = currentPage)
//                .catch { e ->
//                    isFetching = false
//                    _newsResult.value = NewsUiState.Error(e.localizedMessage ?: "Unknown error")
//                }
//                .collect { response ->
//                    isFetching = false
//                    allArticles.clear()
//                    allArticles.addAll(response.articles)
//                    _newsResult.value =
//                        NewsUiState.Success(NewsResponse(articles = allArticles.toList()))
//                }

            if (allArticles.isEmpty()) {
                _newsResult.value = NewsUiState.Loading
            }
            try {
                val response = newsRepository.getNewsSuspendly(page = currentPage)
                allArticles.clear()
                allArticles.addAll(response.articles)
                _newsResult.value =
                    NewsUiState.Success(NewsResponse(articles = allArticles.toList()))
            } catch (e: Exception) {
                _newsResult.value = NewsUiState.Error(e.localizedMessage ?: "Error in call")
            } finally {
                isFetching = false
            }
        }
    }

    fun loadMore() {
        if (isFetching) return
        isFetching = true
        currentPage++
        viewModelScope.launch {
//            newsRepository.getNews(page = currentPage)
//                .catch { e ->
//                    isFetching = false
//                    currentPage--
//                }
//                .collect { response ->
//                    isFetching = false
//                    if (response.articles.isNotEmpty()) {
//                        allArticles.addAll(response.articles)
//                        _newsResult.value =
//                            NewsUiState.Success(NewsResponse(articles = allArticles.toList()))
//                    }
//                }

            try {
                val response = newsRepository.getNewsSuspendly(page = currentPage)
                allArticles.addAll(response.articles)
                _newsResult.value =
                    NewsUiState.Success(NewsResponse(articles = allArticles.toList()))

            } catch (e: Exception) {
                _newsResult.value = NewsUiState.Error(e.localizedMessage ?: "Error in call")
                currentPage--
            } finally {
                isFetching = false
            }
        }
    }
}
