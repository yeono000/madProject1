package com.yeono.madproject1.ui.dashboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeono.madproject1.R
import com.yeono.madproject1.RVAdapter
import com.yeono.madproject1.databinding.FragmentDashboardDetailBinding
import com.yeono.madproject1.databinding.FragmentFriendsBinding
import com.yeono.madproject1.ui.FriendAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Friends.newInstance] factory method to
 * create an instance of this fragment.
 */
class Friends : Fragment() {
    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val argument = requireArguments()
        val imageData = argument?.getParcelable<RealFriendViewModel>("FrId")
        val imageList = imageData?.imageList ?: emptyList()
        /*if(imageList != null){
            Log.d("image", imageData.toString())
        }*/
        var rv = binding.recyclerView2
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.addItemDecoration(DividerItemDecoration(requireContext(), 1))
        rv.adapter = RVAdapter(imageList)
        return root
    }

}