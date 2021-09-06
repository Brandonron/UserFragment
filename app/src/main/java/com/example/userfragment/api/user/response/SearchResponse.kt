package com.example.userfragment.api.user.response

class SearchResponse(
    val incomplete_results: Boolean,
    val items: List<SearchListResponse>,
    val total_count: Int
)