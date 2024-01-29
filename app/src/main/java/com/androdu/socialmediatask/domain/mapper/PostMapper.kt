package com.androdu.socialmediatask.domain.mapper

import com.androdu.socialmediatask.data.remote.model.PostDto
import com.androdu.socialmediatask.domain.model.Post

fun PostDto.toPost(): Post {
    return Post(
        id = id ?: 0,
        title = title ?: "No Title",
        body = body ?: "No Body"
    )
}