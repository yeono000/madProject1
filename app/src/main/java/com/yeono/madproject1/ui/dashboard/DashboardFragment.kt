package com.yeono.madproject1.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yeono.madproject1.R
import com.yeono.madproject1.RVAdapter
import com.yeono.madproject1.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var imageFriendList: MutableList<Int> = mutableListOf()

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
            // 버튼이 클릭되었을 때 네비게이션을 이용해 fragment_friends로 전환합니다.
            //imageFriendList.add(imageList[0])
            //imageFriendList.add(R.drawable.bts_5)
            //imageFriendList.add(R.drawable.bts_6)
            val data = RealFriendViewModel(imageFriendList)
            val action = DashboardFragmentDirections.actionNavigationDashboardToFriends(data)
           // val action = DashboardFragmentDirections.actionNavigationDashboardToDashboardDetailFragment(data)
            findNavController().navigate(action)
        }

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
            Log.d("imageList", "!!!")

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
            adapter.setItemClickListener(object: RVAdapter.OnItemClickListener{
                override fun onClick(position: Int) {
                    Log.d("position", position.toString())
                    imageFriendList.add(imageList[position])
                    val data = ImageDataModel(position.toString(), imageList[position])
                    val action = DashboardFragmentDirections.actionNavigationDashboardToDashboardDetailFragment(data)

                    findNavController().navigate(action)
                }
            })

            //adapter = RVAdapter()
        }

        binding.save.setOnClickListener {
            val database = Firebase.database
            val myRef = database.getReference("message")

            myRef.push().setValue("Hello, World!")
        }

        /*var auth = Firebase.auth


        binding.button.setOnClickListener {

            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {


                        val user = auth.currentUser

                        Log.d("MainActivity", user!!.uid)

                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show()

                    }
                }

        }*/

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