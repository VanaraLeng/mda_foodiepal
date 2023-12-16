package com.vanaraleng.foodiepal.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vanaraleng.foodiepal.MainActivity
import com.vanaraleng.foodiepal.R
import com.vanaraleng.foodiepal.data.Mock
import com.vanaraleng.foodiepal.databinding.FragmentMealPlannerBinding
import com.vanaraleng.foodiepal.models.MealPlan
import com.vanaraleng.foodiepal.models.Recipe
import java.util.Calendar
import java.util.Date

class MealPlanerFragment : Fragment() {

    private var _binding: FragmentMealPlannerBinding? = null

    val mealPlans = Mock.mockMealPlans.toMutableList()

    private val recipes: MutableList<Recipe>
        get() =  (activity as MainActivity).recipes

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealPlannerBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = MealPlannerAdapter(mealPlans)
        binding.recyclerView.adapter = adapter
        binding.fab.setOnClickListener { showDialog() }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.plan_add_title))

        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.VERTICAL

        val contentView = LinearLayout(context)
        contentView.orientation = LinearLayout.VERTICAL
        val layoutParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val space = resources.getDimensionPixelSize(R.dimen.dialog_field_padding)
        layoutParam.setMargins(space,space,space,space)
        contentView.layoutParams = layoutParam
        linearLayout.addView(contentView)

        // Breakfast
        val breakfastLayout = LinearLayout(context)
        contentView.addView(breakfastLayout)

        val breakfastTv = createTitleLabel(getString(R.string.plan_breakfast))
        breakfastLayout.addView(breakfastTv)

        val breakfastSpinner = Spinner(context, Spinner.MODE_DROPDOWN)
        breakfastSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, recipes)
        breakfastLayout.addView(breakfastSpinner)

        // Lunch
        val lunchLayout = LinearLayout(context)
        contentView.addView(lunchLayout)

        val lunchTv = createTitleLabel(getString(R.string.plan_lunch))
        lunchLayout.addView(lunchTv)

        val lunchRecipeSpinner = Spinner(context, Spinner.MODE_DROPDOWN)
        lunchRecipeSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, recipes)
        lunchLayout.addView(lunchRecipeSpinner)

        // Dinner
        val dinnerLayout = LinearLayout(context)
        contentView.addView(dinnerLayout)

        val dinnerTv = createTitleLabel(getString(R.string.plan_dinner))
        dinnerLayout.addView(dinnerTv)

        val dinnerRecipeSpinner = Spinner(context, Spinner.MODE_DROPDOWN)
        dinnerRecipeSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, recipes)
        dinnerLayout.addView(dinnerRecipeSpinner)

        val datePicker = DatePicker(context)
        linearLayout.addView(datePicker)

        builder.setView(linearLayout)
        builder.setPositiveButton(getString(R.string.plan_plan_button)) { _, _ ->
            val breakfast = breakfastSpinner.selectedItem as? Recipe
            val lunch = lunchRecipeSpinner.selectedItem as? Recipe
            val dinner = dinnerRecipeSpinner.selectedItem as? Recipe
            val date = getDateFromDatePicker(datePicker)
            date?.let {
                addPlan(date, breakfast, lunch, dinner)
            }
        }
        builder.setNegativeButton(getString(R.string.cancel)) { _, _ -> }
        builder.show()
    }

    fun getDateFromDatePicker(datePicker: DatePicker): Date? {
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.getTime()
    }

    fun createTitleLabel(text: String): TextView {
        val textView = TextView(context)
        textView.text = text
        textView.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Body1)
        textView.setTextColor(androidx.appcompat.R.attr.colorAccent)
        return textView
    }

    private fun addPlan(date: Date, breakfast: Recipe?, lunch: Recipe?, dinner: Recipe?) {
        val plan = MealPlan(date, breakfast, lunch, dinner)
        mealPlans.add(plan)
        binding.recyclerView.adapter?.notifyItemInserted(mealPlans.count())
        Toast.makeText(context, getString(R.string.plan_added_success), Toast.LENGTH_LONG).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}