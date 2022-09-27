package com.special.mock

import com.special.mock.model.EvCharger
import com.special.mock.api.DummyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val serviceKey = "Tl5XG73zbV2k9UN24evKVcpX23qRJfh9m94o22puvNJxI9V0qCDwkhP0hIK4mUd+0NjId+R/MKIYguGLzLeyVQ=="

class ChargerMockDataImpl @Inject constructor(apiManager: ApiManager) {
    private val client = apiManager.create(DummyApi::class.java)

    suspend fun getChargers(page: Int, pageSize: Int): Result<List<EvCharger>> {
        return withContext(Dispatchers.IO) {
            runCatching { client.getEvChargerList(page = page, size = pageSize, key = serviceKey).data }
        }
    }

}