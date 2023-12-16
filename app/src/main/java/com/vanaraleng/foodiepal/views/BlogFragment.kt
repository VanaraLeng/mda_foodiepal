package com.vanaraleng.foodiepal.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vanaraleng.foodiepal.MainActivity
import com.vanaraleng.foodiepal.R
import com.vanaraleng.foodiepal.data.Constant
import com.vanaraleng.foodiepal.data.Mock
import com.vanaraleng.foodiepal.databinding.FragmentBlogBinding
import com.vanaraleng.foodiepal.models.Blog
import com.vanaraleng.foodiepal.models.BlogComment
import com.vanaraleng.foodiepal.models.Recipe
import com.vanaraleng.foodiepal.utils.PreferenceUtil

class BlogFragment : Fragment() {

    private var _binding: FragmentBlogBinding? = null

    val blogs = Mock.mockBlogs.toMutableList()

    private val recipes: MutableList<Recipe>
        get() =  (activity as MainActivity).recipes

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlogBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = BlogAdapter(blogs)
        binding.recyclerView.adapter = adapter
        adapter.onItemClick = { position ->
            showCommentDialog(position)
        }

        binding.fab.setOnClickListener { showPostDialog() }
    }

    private fun showCommentDialog(position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.blog_add_comment_title))

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

        val editText = EditText(context)
        editText.hint = getString(R.string.blog_comment_hint)
        contentView.addView(editText)

        builder.setView(linearLayout)
        builder.setPositiveButton(getString(R.string.blog_add_comment_button)) { _, _ ->
            val preferenceUtil = activity?.let { PreferenceUtil(it.applicationContext) }
            val user = preferenceUtil?.getString(Constant.username) ?: getString(R.string.anonymous)

            val comment = BlogComment(user, editText.text.toString())
            blogs[position].comments.add(comment)
            binding.recyclerView.adapter?.notifyItemChanged(position)
        }
        builder.setNegativeButton(getString(R.string.cancel)) { _, _ -> }
        builder.show()
    }

    private fun showPostDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.blog_compose_blog_title))

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
        titleEditText.hint = getString(R.string.blog_title_hint)
        contentView.addView(titleEditText)

        val bodyEditText = EditText(context)
        bodyEditText.hint = getString(R.string.blog_body_hint)
        contentView.addView(bodyEditText)

        builder.setView(linearLayout)
        builder.setPositiveButton(getString(R.string.blog_post)) { _, _ ->
            val preferenceUtil = activity?.let { PreferenceUtil(it.applicationContext) }
            val user = preferenceUtil?.getString(Constant.username) ?: getString(R.string.anonymous)

            val blog = Blog(
                user,
                "Now",
                titleEditText.text.toString(),
                bodyEditText.text.toString(),
                mutableListOf()
            )
            blogs.add(blog)
            binding.recyclerView.adapter?.notifyItemChanged(blogs.count())

            Snackbar.make(binding.fab,
                getString(R.string.blog_blog_posted_success), Snackbar.LENGTH_LONG).show()
        }
        builder.setNegativeButton(getString(R.string.cancel)) { _, _ -> }
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}