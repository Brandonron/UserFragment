package com.example.userfragment.adapter.search.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.userfragment.api.user.UserApiManager
import com.example.userfragment.api.user.response.SearchListResponse

class SearchPageSource(
    private var name: String?
) : PagingSource<Int, SearchListResponse>() {
    override fun getRefreshKey(state: PagingState<Int, SearchListResponse>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchListResponse> {
        return try {
            val page = params.key ?: 1 // set page 1 as default
            val pageSize = params.loadSize
            val repoResponse = UserApiManager.getSearch(name, page)
            val repoItems = repoResponse.items
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}