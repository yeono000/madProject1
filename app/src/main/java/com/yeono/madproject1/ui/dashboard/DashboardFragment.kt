package com.yeono.madproject1.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yeono.madproject1.Bts1Activity
import com.yeono.madproject1.R
import com.yeono.madproject1.RVAdapter
import com.yeono.madproject1.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = "Hello World"
        }

        //recyclerView = findViewById(R.id.recyclerView)
        //imageAdapter = ImageAdapter(imageList)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            //layoutManager = LinearLayoutManager(this@DashboardFragment)
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

            /*val adapter = RVAdapter(imageList, object : AdapterView.OnItemClickListener {
                override fun onItemClick(imageResId: Int) {
                    val intent = Intent(requireContext(), Bts1Activity::class.java)
                    intent.putExtra("imageResId", imageResId)
                    startActivity(intent)
                }
            })

            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                this.adapter = adapter
            }*/
            val adapter = RVAdapter(imageList)
            this.adapter = adapter
            //adapter = RVAdapter()
        }

        /*val imageView: ImageView = binding.btsImage1
        imageView.setImageResource(R.drawable.bts_1)*/
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


/*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        imageAdapter = ImageAdapter(imageList)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = imageAdapter
        }
    }*/

/*override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)


    // 1. 화면이 클릭되었다는 것을 알아야 합니다! (프로그램이)
    val image1 = findViewById<ImageView>(R.id.btsImage1)
    image1.setOnClickListener {

        Toast.makeText(this, "1번 클릭 완료", Toast.LENGTH_LONG).show()

        // 2. 화면이 클릭되면, 다음 화면으로 넘어가서, 사진을 크게 보여줌!
        val intent = Intent(this, Bts1Activity::class.java)
        // intent.putExtra(name:"data", value:"1")

        startActivity(intent)

    }
} */