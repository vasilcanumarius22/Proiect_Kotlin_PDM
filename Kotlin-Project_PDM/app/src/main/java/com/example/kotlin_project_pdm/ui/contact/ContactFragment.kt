package com.example.kotlin_project_pdm.ui.contact

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.kotlin_project_pdm.R
import com.example.kotlin_project_pdm.databinding.FragmentContactBinding
import com.example.kotlin_project_pdm.databinding.FragmentSlideshowBinding
import com.example.kotlin_project_pdm.ui.slideshow.SlideshowViewModel

class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null

//    companion object {
//        fun newInstance() = ContactFragment()
//    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

//    private lateinit var viewModel: ContactViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contactViewModel =
            ViewModelProvider(this).get(ContactViewModel::class.java)

        _binding = FragmentContactBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.tvNameLabel
        contactViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val view = inflater.inflate(R.layout.fragment_contact, container, false)

        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            // Handle the Submit button click event
            val etName = view?.findViewById<EditText>(R.id.etName)
            val etEmail = view?.findViewById<EditText>(R.id.etEmail)
            val etMessage = view?.findViewById<EditText>(R.id.etMessage)

            val name = etName?.text.toString()
            val email = etEmail?.text.toString()
            val message = etMessage?.text.toString()

            val toastMessage = "Name: $name, Email: $email, Message: $message"
            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_LONG).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}