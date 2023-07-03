package com.yeono.madproject1.ui.contact

import android.R
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yeono.madproject1.MainActivity
import com.yeono.madproject1.databinding.FragmentContactBinding
import com.yeono.madproject1.ui.home.HomeFragment


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
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.READ_CONTACTS),
                    READ_CONTACTS_REQUEST
                )
            } else {
                getContacts()
            }
        } else {
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

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), 1))

        val adapter: ContactLVAdapter = ContactLVAdapter(dataList)
        val mainActivity = activity as MainActivity
        adapter.setItemClickListener(object: ContactLVAdapter.OnItemClickListener{
            override fun onClick(position: Int) {
                val action = ContactFragmentDirections.actionNavigationContactToContactDetailFragment(dataList[position])
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
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI
        )
        val selection = ContactsContract.CommonDataKinds.Phone.TYPE + " = " + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE

        val cursor = requireContext().contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            selection,
            null,
            null
        )
        cursor?.use { cursor ->
            val nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val phoneNumberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val photoUriColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)

            while (cursor.moveToNext()) {
                val name = cursor.getString(nameColumnIndex)
                val phoneNumber = cursor.getString(phoneNumberColumnIndex)
                var photoUriString = cursor.getString(photoUriColumnIndex)
                var photo = if (!photoUriString.isNullOrEmpty()) getBitmapFromUri(Uri.parse(photoUriString)) else null
                dataList.add(ContactDataModel(name, getFormattedPhoneNumber(phoneNumber), photo))
            }
        }
    }

    private fun getFormattedPhoneNumber(phoneNumber: String): String {
        val digitsOnly = phoneNumber.replace(Regex("[^\\d]"), "")

        val formattedNumber = StringBuilder()
        for (i in digitsOnly.indices) {
            formattedNumber.append(digitsOnly[i])
            if (i == 2 || i == 6) {
                formattedNumber.append("-")
            }
        }

        return formattedNumber.toString()
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_CONTACTS_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContacts()
            } else {
                Toast.makeText(requireContext(), "연락처 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}