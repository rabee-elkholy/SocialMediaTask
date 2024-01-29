package com.androdu.socialmediatask.feature.details_fragment

import com.androdu.socialmediatask.domain.model.Post

data class DetailsState(
    val error: String = "",
    val isLoading: Boolean = false,
    val post: Post? = null
)