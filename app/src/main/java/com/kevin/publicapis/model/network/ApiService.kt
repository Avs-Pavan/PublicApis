package com.kevin.publicapis.model.network

import com.kevin.publicapis.model.ApiResponseItem
import com.kevin.publicapis.model.Constants.GET_PUBLIC_APIS
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(GET_PUBLIC_APIS)
    suspend fun getData(): Response<ApiResponseItem>
}