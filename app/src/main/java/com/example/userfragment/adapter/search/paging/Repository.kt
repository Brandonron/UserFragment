package com.example.userfragment.adapter.search.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.userfragment.api.user.response.SearchListResponse
import kotlinx.coroutines.flow.Flow

object  Repository {
    private const val PAGE_SIZE = 100

    fun getPagingData(name: String?): Flow<PagingData<SearchListResponse>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { SearchPageSource(name) }
        ).flow
    }
}