package com.vanaraleng.foodiepal.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vanaraleng.foodiepal.R
import com.vanaraleng.foodiepal.data.Mock
import com.vanaraleng.foodiepal.databinding.FragmentAboutBinding
import com.vanaraleng.foodiepal.models.Info

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null

    private val binding get() = _binding!!

    private val infos = Mock.infos.toMutableList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)


        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = AboutAdapter(infos)

        binding.fab.setOnClickListener { showAddDialog() }

        return binding.root
    }

    private fun showAddDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.about_add_new_field))

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

        // Title
        val titleEditText = EditText(context)
        titleEditText.hint = getString(R.string.about_field_name_hint)
        contentView.addView(titleEditText)

        val bodyEditText = EditText(context)
        bodyEditText.hint = getString(R.string.about_value_hint)
        contentView.addView(bodyEditText)

        builder.setView(linearLayout)
        builder.setPositiveButton(getString(R.string.about_add_button)) { _, _ ->
            val info = Info( titleEditText.text.toString(), bodyEditText.text.toString())
            infos.add(info)
            binding.recyclerView.adapter?.notifyItemChanged(infos.count())

            Snackbar.make(binding.fab, getString(R.string.about_add_success), Snackbar.LENGTH_LONG).show()
        }
        builder.setNegativeButton(getString(R.string.cancel)) { _, _ -> }
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}