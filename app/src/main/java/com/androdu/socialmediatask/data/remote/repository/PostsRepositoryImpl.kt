package com.androdu.socialmediatask.data.remote.repository

import android.util.Log
import com.androdu.socialmediatask.data.remote.PostsApi
import com.androdu.socialmediatask.domain.mapper.toPost
import com.androdu.socialmediatask.domain.model.Post
import com.androdu.socialmediatask.domain.repository.PostsRepository
import com.androdu.socialmediatask.utils.ApiResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRepositoryImpl @Inject constructor(
    private val postsApi: PostsApi
) : PostsRepository {
    override suspend fun getPosts(): ApiResult<List<Post>> {
        return try {
            val result = postsApi.getPosts()
            if (result.isSuccessful) {
                ApiResult.Success(result.body()!!.map { it.toPost() })
            } else {
                ApiResult.Error(result.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResult.Error(e.message ?: "There was an error!")
        }
    }

    override suspend fun getPostDetails(postId: Int): ApiResult<Post> {
        return try {
            val result = postsApi.getPostDetails(postId = postId)
            if (result.isSuccessful) {
                ApiResult.Success(result.body()!!.toPost())
            } else {
                ApiResult.Error(result.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResult.Error(e.message ?: "There was an error!")
        }
    }
}