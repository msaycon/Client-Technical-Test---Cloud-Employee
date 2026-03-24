package com.cloudemployee.baseproject.data.remote.api

import com.cloudemployee.baseproject.data.remote.dto.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {
    @GET("2.3/users")
    suspend fun getUsers(
        @Query("order") order: String,
        @Query("sort") sort: String,
        @Query("filter") filter: String,
        @Query("site") site: String,
        @Query("key") key: String,
        @Query("pagesize") pageSize: Int,
        @Query("inname") inName: String,
    ): UsersResponse
}
