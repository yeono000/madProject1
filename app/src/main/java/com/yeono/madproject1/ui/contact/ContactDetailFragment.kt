package com.yeono.madproject1.ui.contact

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yeono.madproject1.MainActivity
import com.yeono.madproject1.databinding.FragmentContactBinding
import com.yeono.madproject1.databinding.FragmentDetailContactBinding


class ContactDetailFragment : Fragment() {

    private var _binding: FragmentDetailContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailContactBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val argument = requireArguments()
        val contactData = argument?.getParcelable<ContactDataModel>("contactData")
        if(contactData != null){
            binding.nameText.text = contactData.name
            binding.numberText.text = contactData.number
            if (contactData.photoBitmap != null) {
                binding.profileImage.setImageBitmap(contactData.photoBitmap)
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(requireContext(), "back", Toast.LENGTH_SHORT)
        return super.onOptionsItemSelected(item)
    }
}