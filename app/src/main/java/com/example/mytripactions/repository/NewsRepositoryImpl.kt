package com.example.mytripactions.repository

import com.example.mytripactions.api.NewsApi
import com.example.mytripactions.api.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsApi: NewsApi) : NewsRepository {

    override fun getNews(country: String, pageSize: Int, page: Int): Flow<NewsResponse> = flow {
        emit(newsApi.getNews(country, pageSize, page))
    }
}
