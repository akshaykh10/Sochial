package com.example.sochial

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sochial.models.Post
import kotlinx.android.synthetic.main.item_posts.view.*

class PostsAdapter (val context:Context,val posts:List<Post>):
    RecyclerView.Adapter<PostsAdapter.ViewHolder>(){



        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(post: Post) {
                itemView.tvUsername.text=post.user?.username
                itemView.tvDescription.text=post.description
                Glide.with(context).load(post.imageUrl).into(itemView.ivImage)
                itemView.tvTime.text=DateUtils.getRelativeTimeSpanString(post.creationTime)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
           val view=LayoutInflater.from(context).inflate(R.layout.item_posts,parent,false)
            return ViewHolder(view)
        }

        override fun getItemCount()=posts.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(posts[position])
        }


    }

