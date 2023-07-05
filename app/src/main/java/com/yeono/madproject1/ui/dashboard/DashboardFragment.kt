package com.yeono.madproject1.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.yeono.madproject1.R
import com.yeono.madproject1.RVAdapter
import com.yeono.madproject1.databinding.FragmentDashboardBinding
import com.yeono.madproject1.user.UserModel


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private var imageFriendList: MutableList<Int> = mutableListOf()
    val userViewModel: UserModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.button.setOnClickListener {
            val data = RealFriendViewModel(userViewModel.friendList.value!!)
            val action = DashboardFragmentDirections.actionNavigationDashboardToFriends(data)
           // val action = DashboardFragmentDirections.actionNavigationDashboardToDashboardDetailFragment(data)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.addItemDecoration(DividerItemDecoration(requireContext(), 1))
            var imageList: MutableList<Int> = mutableListOf(R.drawable.bts_1, R.drawable.bts_2, R.drawable.bts_3)
            imageList.add(R.drawable.bts_4)
            imageList.add(R.drawable.bts_5)
            imageList.add(R.drawable.bts_6)
            imageList.add(R.drawable.bts_7)
            imageList.add(R.drawable.member_1)
            imageList.add(R.drawable.member_2)
            imageList.add(R.drawable.member_3)
            imageList.add(R.drawable.member_4)
            imageList.add(R.drawable.member_5)
            imageList.add(R.drawable.member_6)
            imageList.add(R.drawable.member_7)
            imageList.add(R.drawable.member_8)
            imageList.add(R.drawable.member_9)
            Log.d("imageList", "!!!")

            val adapter = RVAdapter(imageList)
            this.adapter = adapter
            adapter.setItemClickListener(object: RVAdapter.OnItemClickListener{
                override fun onClick(position: Int) {
                    Log.d("position", position.toString())
                    imageFriendList.add(imageList[position])
                    userViewModel.addPlayer(position)
                    userViewModel.addFriend(imageList[position])
                    val data = ImageDataModel(position.toString(), imageList[position])
                    val action = DashboardFragmentDirections.actionNavigationDashboardToDashboardDetailFragment(data)

                    findNavController().navigate(action)
                }
            })
        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: RVAdapter

    private val imageList = listOf(
        R.drawable.bts_1,
        R.drawable.bts_2,
        R.drawable.bts_3,
        R.drawable.bts_4,
        R.drawable.bts_5,
        R.drawable.bts_6,
        R.drawable.bts_7,
        // 추가 이미지
    )

}
