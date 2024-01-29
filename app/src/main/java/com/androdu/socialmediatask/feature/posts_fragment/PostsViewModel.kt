package com.androdu.socialmediatask.feature.posts_fragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androdu.socialmediatask.domain.repository.PostsRepository
import com.androdu.socialmediatask.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(PostsState())
    val state = _state.asStateFlow()

    fun setEvent(event: PostsEvents) {
        when (event) {
            PostsEvents.GetPosts -> {
                getPosts()
            }
        }
    }

    private fun getPosts() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val posts = postsRepository.getPosts()
            delay(500) // delay for shimmer animation
            _state.value = when (posts) {
                is ApiResult.Success -> {
                    _state.value.copy(posts = posts.data ?: emptyList(), isLoading = false, error = "")
                }

                is ApiResult.Error -> {
                    _state.value.copy(error = posts.message, isLoading = false)
                }
            }
        }
    }
}