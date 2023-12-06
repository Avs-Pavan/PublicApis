package com.kevin.publicapis.model

import com.kevin.publicapis.model.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class APiRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getEntries(): Response<ApiResponseItem> {
        return apiService.getData()
    }
}