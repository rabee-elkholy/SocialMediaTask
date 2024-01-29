package com.androdu.socialmediatask.feature.details_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.androdu.socialmediatask.R
import com.androdu.socialmediatask.databinding.FragmentDetailsBinding
import com.androdu.socialmediatask.utils.Helper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val mViewModel: PostDetailsViewModel by viewModels()
    private var _binding: FragmentDetailsBinding? = null
    private val mBinding get() = _binding!!

    private var postId = 0
    private val args: DetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        init()
        return mBinding.root
    }


    private fun init() {
        postId = args.postId
        mViewModel.setEvent(DetailsEvents.GetPostDetails(postId = postId))
        setTitle()
        setupCollector()
    }

    private fun setupCollector() {
        lifecycleScope.launch {
            mViewModel.state.collect {
                if (it.isLoading) showShimmer() else hideShimmer()
                if (it.error.isNotEmpty()) Helper.showErrorDialog(requireContext(), desc = it.error)
                if (it.post != null) {
                    mBinding.postDetails = it.post
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTitle() {
        mBinding.detailsTopBar.topBarEtSearch.visibility = View.GONE
        mBinding.detailsTopBar.topBarTvTitle.text = "${getString(R.string.post_id)} $postId"
        mBinding.detailsTopBar.topBarIvBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showShimmer() {
        mBinding.detailsShimmer.visibility = View.VISIBLE
        mBinding.detailsTvTitle.text = ""
        mBinding.detailsTvBody.text = ""
    }

    private fun hideShimmer() {
        mBinding.detailsShimmer.visibility = View.INVISIBLE

    }
}