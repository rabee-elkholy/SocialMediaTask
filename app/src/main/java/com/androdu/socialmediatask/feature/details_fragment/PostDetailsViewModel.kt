package com.androdu.socialmediatask.feature.details_fragment

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
class PostDetailsViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

    fun setEvent(event: DetailsEvents) {
        when (event) {
            is DetailsEvents.GetPostDetails -> {
                getPost(event.postId)
            }
        }
    }

    private fun getPost(postId:Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, post = null, error = "")
            val posts = postsRepository.getPostDetails(postId = postId)
            delay(500) // delay for shimmer animation
            _state.value = when (posts) {
                is ApiResult.Success -> {
                    _state.value.copy(post = posts.data, isLoading = false, error = "")
                }

                is ApiResult.Error -> {
                    _state.value.copy(error = posts.message, isLoading = false, post = null)
                }
            }
        }
    }
}