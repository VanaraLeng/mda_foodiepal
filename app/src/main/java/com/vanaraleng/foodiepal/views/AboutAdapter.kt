package com.vanaraleng.foodiepal.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vanaraleng.foodiepal.R
import com.vanaraleng.foodiepal.models.Info


class AboutAdapter(private val infos: List<Info>):
    RecyclerView.Adapter<AboutAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return infos.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.about_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = infos[position]
        holder.fieldTextView.text = "${info.field}:"
        holder.valueTextView.text = info.value
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val fieldTextView: TextView = itemView.findViewById(R.id.tvField)
        val valueTextView: TextView = itemView.findViewById(R.id.tvValue)
    }
}