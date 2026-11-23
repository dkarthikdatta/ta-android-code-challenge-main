package com.example.mytripactions.repository

import com.example.mytripactions.api.NewsResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(country: String = "us", pageSize: Int = 10, page: Int = 1): Flow<NewsResponse>
}
