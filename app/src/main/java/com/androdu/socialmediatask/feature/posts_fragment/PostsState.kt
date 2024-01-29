package com.androdu.socialmediatask.feature.posts_fragment

import com.androdu.socialmediatask.domain.model.Post

data class PostsState(
    val error: String = "",
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList()
)