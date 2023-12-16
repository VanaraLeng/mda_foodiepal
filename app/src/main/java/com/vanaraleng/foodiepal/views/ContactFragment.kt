package com.vanaraleng.foodiepal.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vanaraleng.foodiepal.R
import com.vanaraleng.foodiepal.data.Mock
import com.vanaraleng.foodiepal.databinding.FragmentContactBinding

class ContactFragment : Fragment() {

    private val phoneNumber = Mock.phoneNumber
    private val email = Mock.email


    private var _binding: FragmentContactBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        binding.btnCall.setOnClickListener { callPhone() }
        binding.btnEmail.setOnClickListener { writeEmail() }
        return binding.root
    }

    private fun callPhone(){
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(context, getString(R.string.error_phone_app_available), Toast.LENGTH_LONG).show()
        }
    }

    private fun writeEmail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData(Uri.parse("mailto:")) // only email apps should handle this

        intent.putExtra(Intent.EXTRA_EMAIL, email)
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.contact_default_subject))
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(context, getString(R.string.error_no_mail_app), Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}