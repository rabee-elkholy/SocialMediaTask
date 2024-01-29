package com.androdu.socialmediatask.feature.posts_fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androdu.socialmediatask.R
import com.androdu.socialmediatask.databinding.FragmentPostsBinding
import com.androdu.socialmediatask.feature.adapter.PostsAdapter
import com.androdu.socialmediatask.utils.Helper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsFragment : Fragment() {

    private val mViewModel: PostsViewModel by viewModels()
    private var _binding: FragmentPostsBinding? = null
    private val mBinding get() = _binding!!

    private var postsAdapter: PostsAdapter = PostsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)

        init()
        return mBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.setEvent(PostsEvents.GetPosts)
        setupCollector()
    }

    private fun init() {
        setTitle()
        setupRecyclerView()
        setupRefresh()
    }

    private fun setupRefresh() {
        mBinding.postsSrlSwipe.setOnRefreshListener {
            mViewModel.setEvent(PostsEvents.GetPosts)
            mBinding.postsTopBar.topBarEtSearch.text.clear()
            mBinding.postsSrlSwipe.isRefreshing = false
        }
    }

    private fun setupCollector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.state.collect {
                    Log.d("TAG", "getPosts: it = ")

                    if (it.isLoading) showShimmer() else hideShimmer()
                    if (it.error.isNotEmpty()) Helper.showErrorDialog(requireContext(), desc = it.error)
                    if (it.posts.isNotEmpty()) {
                        postsAdapter.setData(posts = it.posts)
                    } else {
                        postsAdapter.clearData()
                    }
                }
            }
        }

    }

    private fun setupRecyclerView() {
        postsAdapter.setOnItemClickListener {
            findNavController().navigate(
                PostsFragmentDirections.actionPostsFragmentToDetailsFragment(
                    postId = it
                )
            )
        }

        mBinding.postsRvPosts.apply {
            adapter = postsAdapter
            val mLayoutManager = LinearLayoutManager(requireContext())
            layoutManager = mLayoutManager
        }
    }

    private fun setTitle() {
        mBinding.postsTopBar.topBarIvBack.visibility = View.GONE
        mBinding.postsTopBar.topBarTvTitle.text = getString(R.string.posts)
        mBinding.postsTopBar.topBarEtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(txt: Editable?) {
                postsAdapter.filter.filter(txt)
            }
        })
    }

    private fun showShimmer() {
        mBinding.postsShimmerView.visibility = View.VISIBLE
        mBinding.postsRvPosts.visibility = View.GONE
    }

    private fun hideShimmer() {
        mBinding.postsRvPosts.visibility = View.VISIBLE
        mBinding.postsShimmerView.visibility = View.GONE
    }
}