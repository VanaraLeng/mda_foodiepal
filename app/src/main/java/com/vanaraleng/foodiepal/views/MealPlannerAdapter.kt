package com.vanaraleng.foodiepal.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vanaraleng.foodiepal.R
import com.vanaraleng.foodiepal.models.MealPlan
import java.time.Instant
import java.time.ZoneId


class MealPlannerAdapter(private val plans: List<MealPlan>):
    RecyclerView.Adapter<MealPlannerAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return plans.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plan_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plan = plans[position]
        holder.breakfastNameTextView.text = plan.breakfast?.name
        holder.lunchNameTextView.text = plan.lunch?.name
        holder.dinnerNameTextView.text = plan.dinner?.name

        val localDate = Instant.ofEpochMilli(plan.date.time).atZone(ZoneId.systemDefault()).toLocalDate()
        holder.dayOfWeekTextView.text = localDate.dayOfWeek.name
        holder.dayTextView.text = localDate.dayOfMonth.toString()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val breakfastNameTextView: TextView = itemView.findViewById(R.id.tvBreakfastName)
        val lunchNameTextView: TextView = itemView.findViewById(R.id.tvLunchName)
        val dinnerNameTextView: TextView = itemView.findViewById(R.id.tvDinnerName)

        val dayOfWeekTextView:TextView = itemView.findViewById(R.id.tvDayOfWeek)
        val dayTextView: TextView = itemView.findViewById(R.id.tvMessage)
    }
}