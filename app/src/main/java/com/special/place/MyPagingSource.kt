package com.special.place

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.special.domain.entities.Paging

typealias PagingDataSourceDef<T> = suspend ((Int) -> Paging<T>)

class MyPagingSource<T : Any>(
    private val source: PagingDataSourceDef<T>
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        try {
            val position = params.key ?: 0
            val result = source(position)

            return if (result.list.isNotEmpty()) {
                LoadResult.Page(
                    data = result.list,
                    prevKey = if (position == 0) null else position - 1,
                    nextKey = if (result.isLast) null else position + 1
                )
            } else {
                LoadResult.Invalid()
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }

}