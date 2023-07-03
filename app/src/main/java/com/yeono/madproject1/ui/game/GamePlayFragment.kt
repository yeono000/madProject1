package com.yeono.madproject1.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yeono.madproject1.databinding.FragmentGameBinding
import com.yeono.madproject1.databinding.FragmentPlayBinding
import com.yeono.madproject1.databinding.FragmentPlayerBinding
import com.yeono.madproject1.ui.game.PlayerDataModel

class GamePlayFragment() : Fragment() {
    private var _binding: FragmentPlayBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val notificationsViewModel =
//            ViewModelProvider(this).get(GameViewModel::class.java)
        _binding = FragmentPlayBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}