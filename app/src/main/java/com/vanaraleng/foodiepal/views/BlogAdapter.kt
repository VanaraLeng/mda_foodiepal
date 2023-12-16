package com.vanaraleng.foodiepal.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vanaraleng.foodiepal.R
import com.vanaraleng.foodiepal.models.Blog
import com.vanaraleng.foodiepal.models.BlogComment


class BlogAdapter(private val blogs: List<Blog>, var onItemClick: ((Int) -> Unit)? = null):
    RecyclerView.Adapter<BlogAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return blogs.count()
    }

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blog_list_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blog = blogs[position]
        holder.titleTextView.text = blog.title
        holder.usernameTextView.text = blog.username
        holder.timeTextView.text = blog.postedTime
        holder.messageTextView.text = blog.text
        val adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, blog.comments)
        holder.commentListView.adapter = adapter
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.tvTitle)
        val usernameTextView: TextView = itemView.findViewById(R.id.tvUsername)
        val timeTextView: TextView = itemView.findViewById(R.id.tvTime)
        val messageTextView: TextView = itemView.findViewById(R.id.tvMessage)
        val addButton: TextView = itemView.findViewById(R.id.addButton)
        val commentListView: ListView = itemView.findViewById(R.id.listView)

        init {
            addButton.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
        }
    }
}