package com.example.mystoryapplication.api

import com.example.mystoryapplication.api.response.AddNewStoryResponse
import com.example.mystoryapplication.api.response.GetAllStoriesResponse
import com.example.mystoryapplication.api.response.GetDetailStoriesResponse
import com.example.mystoryapplication.api.response.LoginResponse
import com.example.mystoryapplication.api.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int
    ): GetAllStoriesResponse

    @GET("stories/{id}")
    suspend fun getStorybyId(
        @Path("id") id: String
    ) : GetDetailStoriesResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ) : AddNewStoryResponse

}