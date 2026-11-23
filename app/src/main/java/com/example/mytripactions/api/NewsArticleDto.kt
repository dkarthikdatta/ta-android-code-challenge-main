package com.example.mytripactions.api

data class SourceDto(
    val id: String?,
    val name: String?
)

data class NewsArticleDto(
    val source: SourceDto? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null
)
