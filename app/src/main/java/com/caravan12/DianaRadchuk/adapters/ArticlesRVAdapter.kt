package com.caravan12.DianaRadchuk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caravan12.DianaRadchuk.R

class ArticlesRVAdapter(private val articlesList: Array<String>): RecyclerView.Adapter<ArticlesRVAdapter.MyViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    class MyViewHolder(itemView: View, listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        val articleTitle : TextView = itemView.findViewById(R.id.textViewArticle)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_article_layout, parent, false)
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = articlesList[position]
        holder.articleTitle.text = currentItem
    }

    override fun getItemCount(): Int {
        return articlesList.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }
}
