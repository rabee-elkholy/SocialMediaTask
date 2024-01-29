package com.androdu.socialmediatask.data.remote

import com.androdu.socialmediatask.data.remote.model.PostDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PostsApi {
    @GET("posts")
    suspend fun getPosts(
    ): Response<List<PostDto>>

    @GET("posts/{postId}")
    suspend fun getPostDetails(
        @Path("postId") postId: Int,
    ): Response<PostDto>
}