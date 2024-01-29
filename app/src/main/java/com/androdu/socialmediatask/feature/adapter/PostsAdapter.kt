package com.androdu.socialmediatask.feature.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androdu.socialmediatask.databinding.PostListItemBinding
import com.androdu.socialmediatask.domain.model.Post
import com.androdu.socialmediatask.utils.AdapterDiffUtil

@SuppressLint("NotifyDataSetChanged")
class PostsAdapter : RecyclerView.Adapter<PostsAdapter.MyViewHolder>(), Filterable {
    private var originalPostsList = mutableListOf<Post>()
    private var postsList = mutableListOf<Post>()

    private var listener: OnItemClickListener? = null

    class MyViewHolder(private val binding: PostListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post, listener: OnItemClickListener?) {
            binding.post = post
            binding.root.setOnClickListener {
                listener?.onClick(post.id)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PostListItemBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPost = postsList[position]
        holder.bind(currentPost, listener)
    }

    fun setData(posts: List<Post>) {
        // Copy the original list of photos
        originalPostsList.clear()
        originalPostsList.addAll(posts)
        // Apply the DiffUtil to update the photosList
        val photoDiffUtil = AdapterDiffUtil(postsList, posts)
        val photoDiffUtilResult = DiffUtil.calculateDiff(photoDiffUtil)
        postsList = posts.toMutableList()
        photoDiffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearData() {
        setData(mutableListOf())
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<Post>()
                if (constraint.isNullOrEmpty()) {
                    // If the query is empty, return the original list
                    filteredList.addAll(originalPostsList)
                } else {
                    // Otherwise, filter the list by matching the photo title with the query
                    val filterPattern = constraint.toString().lowercase().trim()
                    originalPostsList.forEach {
                        if (it.title.contains(filterPattern))
                            filteredList.add(it)
                    }
                    println(originalPostsList.size)
                    println(filteredList.size)
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                // Update the photosList with the filtered results and notify the adapter
                postsList.clear()
                postsList.addAll(results?.values as MutableList<Post>)
                notifyDataSetChanged()
            }
        }
    }

    fun interface OnItemClickListener {
        fun onClick(postId: Int)
    }
}