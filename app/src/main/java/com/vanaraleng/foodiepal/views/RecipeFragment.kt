package com.vanaraleng.foodiepal.views

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import com.vanaraleng.foodiepal.MainActivity
import com.vanaraleng.foodiepal.R
import com.vanaraleng.foodiepal.databinding.FragmentRecipesBinding
import com.vanaraleng.foodiepal.models.Recipe

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private val recipes: MutableList<Recipe>
        get() =  (activity as MainActivity).recipes

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        val adapter = RecipeAdapter(recipes)
        binding.recyclerView.adapter = adapter

        // Update rating
        adapter.onRatingChange = { pos, rating ->
            val oldRating = recipes[pos].rating
            recipes[pos].rating = rating

            val snackbar = Snackbar.make(binding.recyclerView,
                getString(R.string.recipe_rating_updated), Snackbar.LENGTH_LONG)
            snackbar.setAction(getString(R.string.undo)) {
                recipes[pos].rating = oldRating
                binding.recyclerView.adapter?.notifyItemChanged(pos)
            }
            snackbar.show()
        }

        binding.fab.setOnClickListener { showDialog() }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.recipe_add_title))

        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.VERTICAL

        val contentView = LinearLayout(context)
        contentView.orientation = LinearLayout.VERTICAL
        val layoutParam = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        val space = resources.getDimensionPixelSize(R.dimen.dialog_field_padding)
        layoutParam.setMargins(space,space,space,space)
        contentView.layoutParams = layoutParam
        linearLayout.addView(contentView)

        // Name
        val nameEditText = EditText(context)
        nameEditText.hint = getString(R.string.add_recipe_name_field)
        nameEditText.inputType = InputType.TYPE_CLASS_TEXT
        contentView.addView(nameEditText)

        // Time to cook
        val timeToCookTV = TextView(context)
        timeToCookTV.text = getString(R.string.time_to_cook)
        timeToCookTV.setTextAppearance(androidx.transition.R.style.Base_TextAppearance_AppCompat_Body2)
        val color = MaterialColors.getColor(requireContext(), android.R.attr.colorAccent, resources.getColor(R.color.black, requireContext().theme))
        timeToCookTV.setTextColor(color)
        contentView.addView(timeToCookTV)

        val timePicker = TimePicker(context)
        timePicker.hour = 0
        timePicker.minute = 0
        timePicker.layoutMode = TimePicker.LAYOUT_MODE_CLIP_BOUNDS
        timePicker.setIs24HourView(true)
        linearLayout.addView(timePicker)

        builder.setView(linearLayout)
        builder.setPositiveButton(getString(R.string.add)) { _, _ ->
            val name = nameEditText.text.toString()
            var timeString: String = ""
            if (timePicker.hour > 0) {
                timeString = "${timePicker.hour} hour "
            } else if (timePicker.hour > 1) {
                timeString = "${timePicker.hour} hours "
            }

            if (timePicker.minute > 1) {
                timeString = "$timeString ${timePicker.minute} mins"
            } else {
                timeString = "$timeString ${timePicker.minute} min"
            }

            addRecipe(name, timeString)
        }
        builder.setNegativeButton(getString(R.string.cancel)) { _, _ -> }
        builder.show()
    }

    private fun addRecipe(name: String, time: String) {
        val recipe = Recipe(name, time, 0, R.drawable.bibimbab)
        recipes.add(recipe)
        binding.recyclerView.adapter?.notifyItemInserted(recipes.count())
        Toast.makeText(context, getString(R.string.recipe_added_success), Toast.LENGTH_LONG).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}