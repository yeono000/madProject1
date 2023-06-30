package com.yeono.madproject1.ui.contact

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yeono.madproject1.databinding.FragmentContactBinding


class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView: RecyclerView = binding.contactRV
        //fragment의 context를 가져옴
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), 1))

        val dataList: MutableList<ContactDataModel> = mutableListOf<ContactDataModel>()
        dataList. add(ContactDataModel("연오","010-3954-1979"))
        dataList. add(ContactDataModel("철수","010-1111-2222"))
        dataList. add(ContactDataModel("미애","010-3333-4444"))

        Log.d("dataList", dataList.toString())

        val adapter: ContactLVAdapter = ContactLVAdapter(dataList)
        recyclerView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}