package com.yeono.madproject1.ui.dashboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.yeono.madproject1.R
import com.yeono.madproject1.databinding.FragmentDashboardBinding
import com.yeono.madproject1.databinding.FragmentDashboardDetailBinding
import com.yeono.madproject1.ui.contact.ContactDataModel

class DashboardDetailFragment : Fragment() {

    private var _binding: FragmentDashboardDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val argument = requireArguments()
        val imageData = argument?.getParcelable<ImageDataModel>("imageId")
        if(imageData != null){
            Log.d("image", imageData.toString())
            //val imageResId = getImageResIDFromImageData(imageData)
            binding.Image.setImageResource(imageData.number)
        }
//        val imageList = argument?.getIntegerArrayList("imageId")
//        if (imageList != null) {
//            binding.textView.textSize = 50f
//            binding.textView.text = (imageList).toString()
//        }

        super.onCreate(savedInstanceState)
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)



        binding.button.setOnClickListener {
            // 버튼이 클릭되었을 때 네비게이션을 이용해 fragment_friends로 전환합니다.
            //val data = ImageDataModel("0", "1")


            // val action = DashboardFragmentDirections.actionNavigationDashboardToDashboardDetailFragment(data)
            val tmp = imageData?.number
            //val action = DashboardDetailFragmentDirections.actionDashboardDetailFragmentToNavigationDashboard(tmp.toString())
            val action = DashboardDetailFragmentDirections.actionDashboardDetailFragmentToNavigationDashboard()
            if (tmp != null) {
                Log.d("image", tmp.toString())
            }
            findNavController().navigate(action)
        }
        return root
    }
}