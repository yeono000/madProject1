package com.yeono.madproject1.ui.contact

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        binding.textView.text = argument?.getInt("contactId").toString()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}