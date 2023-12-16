package com.vanaraleng.foodiepal.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vanaraleng.foodiepal.R
import com.vanaraleng.foodiepal.models.Recipe


class RecipeAdapter(private val recipes: List<Recipe>, var onRatingChange: ((Int, Int) -> Unit)? = null):
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return recipes.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.nameTextView.text = recipe.name
        holder.cookingTimeTextView.text = recipe.cookingTime
        holder.setRating(recipe.rating, false)
        holder.imageView.setImageResource(recipe.image)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tvMessage)
        val cookingTimeTextView: TextView = itemView.findViewById(R.id.tvCookingTime)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)

        private val starList = listOf<ImageView>(
            itemView.findViewById(R.id.star1),
            itemView.findViewById(R.id.star2),
            itemView.findViewById(R.id.star3),
            itemView.findViewById(R.id.star4),
            itemView.findViewById(R.id.star5)
        )

        init {

            starList.forEach { element ->
                element.setOnClickListener { imgView ->
                    when (imgView) {
                        starList[0] -> setRating(1, true)
                        starList[1] -> setRating(2, true)
                        starList[2] -> setRating(3, true)
                        starList[3] -> setRating(4, true)
                        starList[4] -> setRating(5, true)
                    }
                }
            }
        }

        fun setRating(rating: Int, notify: Boolean) {
            for (i in 0 until rating) {
                starList[i].setImageResource(R.drawable.baseline_star_24)
            }
            for (i in rating until starList.count()) {
                starList[i].setImageResource(R.drawable.gray_star_24)
            }
            if (notify) {
                onRatingChange?.invoke(adapterPosition, rating)
            }

        }
    }
}