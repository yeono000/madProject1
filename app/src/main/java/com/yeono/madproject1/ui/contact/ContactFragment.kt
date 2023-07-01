package com.yeono.madproject1.ui.contact

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yeono.madproject1.R
import androidx.navigation.fragment.findNavController
import com.yeono.madproject1.databinding.FragmentContactBinding

class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!
    private val READ_CONTACTS_REQUEST = 101
    private val dataList: MutableList<ContactDataModel> = mutableListOf<ContactDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            )  {
                // 권한이 없으면 요청
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.READ_CONTACTS),
                    READ_CONTACTS_REQUEST
                )
            } else {
                // 권한이 있으면 연락처 정보 가져오기
                getContacts()
            }
        } else {
            // M 버전 이전은 권한이 자동으로 부여되므로 연락처 정보 가져오기
            getContacts()
        }
    }

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

        Log.d("dataList", dataList.toString())

        val adapter: ContactLVAdapter = ContactLVAdapter(dataList)
        adapter.setItemClickListener(object: ContactLVAdapter.OnItemClickListener{
            override fun onClick(position: Int) {
                val action = ContactFragmentDirections.actionContactToDetail(position)
                findNavController().navigate(action)
            }
        })
        recyclerView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getContacts() {
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val selection = ContactsContract.CommonDataKinds.Phone.TYPE + " = " + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE

        val cursor = requireContext().contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            selection,
            null,
            null
        )
        Log.d("cursor", cursor.toString())
        cursor?.use { cursor ->
            val nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val phoneNumberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (cursor.moveToNext()) {
                val name = cursor.getString(nameColumnIndex)
                val phoneNumber = cursor.getString(phoneNumberColumnIndex)
                dataList.add(ContactDataModel(name, getFormattedPhoneNumber(phoneNumber)))
            }
        }
    }

    private fun getFormattedPhoneNumber(phoneNumber: String): String {
        // 숫자 이외의 문자 제거
        val digitsOnly = phoneNumber.replace(Regex("[^\\d]"), "")

        // 형식에 맞게 포맷팅
        val formattedNumber = StringBuilder()
        for (i in digitsOnly.indices) {
            formattedNumber.append(digitsOnly[i])
            if (i == 2 || i == 6) {
                formattedNumber.append("-")
            }
        }

        return formattedNumber.toString()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_CONTACTS_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용되면 연락처 정보 가져오기
                getContacts()
            } else {
                // 권한이 거부되었을 때 처리할 작업
                Toast.makeText(requireContext(), "연락처 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}