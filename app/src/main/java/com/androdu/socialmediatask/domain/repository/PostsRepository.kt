package com.androdu.socialmediatask.domain.repository

import com.androdu.socialmediatask.domain.model.Post
import com.androdu.socialmediatask.utils.ApiResult

interface PostsRepository {
    suspend fun getPosts(): ApiResult<List<Post>>
    suspend fun getPostDetails(postId: Int): ApiResult<Post>
}