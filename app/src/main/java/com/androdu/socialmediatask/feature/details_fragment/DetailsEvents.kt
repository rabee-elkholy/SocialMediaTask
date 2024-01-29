package com.androdu.socialmediatask.feature.details_fragment

sealed class DetailsEvents {
    data class GetPostDetails(val postId:Int) : DetailsEvents()
}