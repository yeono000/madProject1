package com.yeono.madproject1.ui.game

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.yeono.madproject1.R
import com.yeono.madproject1.databinding.FragmentPlayBinding
import com.yeono.madproject1.user.UserModel
import java.util.Random

class GamePlayFragment() : Fragment() {
    private var _binding: FragmentPlayBinding? = null
    private val binding get() = _binding!!
    private lateinit var playData: PlayModel
    private var playerDataList: MutableList<PlayerModel> = mutableListOf<PlayerModel>()
    val userViewModel: UserModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playData = ViewModelProvider(this).get(PlayModel::class.java)
        playData.setPlayerIds(userViewModel.playerList.value!!.map{
            user -> user.uid!!
        })
        playData.setPlayerNames(userViewModel.playerList.value!!.map{
                user -> user.name?:"player"
        })
//        playData.setCards(shuffleArray(arrayOf(1,1,1,1,1,2,2,3,3,4,4,5,5,6,7,8)))
        playerDataList = mutableListOf<PlayerModel>()
        if(playData != null){
            for(i in 0 until playData.numberOfPlayer.value!!){
                val data = PlayerModel()
                data.setPlayer(playData.playerIds.value!!.get(i), playData.playerNames.value!!.get(i))
                data.setCard(playData.getCard())
                data.setPlayerNumber(i)
                playData.setTurn()
                playerDataList.add(data)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayBinding.inflate(inflater, container, false)
//        Log.d("sibal", data1.toString())
//        val data2 = ViewModelProvider(this).get(PlayerViewModel::class.java)
        val root: View = binding.root
        val adapter = PlayerListAdapter(playData, viewLifecycleOwner, playerDataList, requireContext())
        binding.listView.adapter = adapter
        var dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.custom_modal)

        var btnClose = dialog.findViewById<Button>(R.id.modal_button)
        btnClose.setOnClickListener {
            playData.playTurn(playerDataList, null)
            dialog.hide()
        }
        dialog.show()
        playData.index.observe(viewLifecycleOwner){ data ->
            if(data == 15){
                dialog.findViewById<TextView>(R.id.modal_text).text = "게임이 종료 되었습니다!"
                dialog.show()
            }
        }
        playData.gameEnd.observe(viewLifecycleOwner){ data ->
            if(data){
                dialog.findViewById<TextView>(R.id.modal_text).text = "게임이 종료 되었습니다!"
                dialog.show()
            }
        }
        val infoDialog = Dialog(requireContext())
        infoDialog.setContentView(R.layout.info_modal)
        binding.cardDum.setOnClickListener{
            infoDialog.show()
        }
        val infoBtnClose = infoDialog.findViewById<Button>(R.id.modal_button)
        infoBtnClose.setOnClickListener {
            infoDialog.hide()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    fun setPlayData(playDataModel: PlayDataModel) {
//        this.playData = playDataModel
//    }
    fun shuffleArray(array: Array<Int>): Array<Int> {
        val shuffledArray = array.copyOf()
        val random = Random()

        for (i in shuffledArray.indices) {
            val randomIndex = random.nextInt(i + 1)
            val temp = shuffledArray[randomIndex]
            shuffledArray[randomIndex] = shuffledArray[i]
            shuffledArray[i] = temp
        }
        return shuffledArray
    }
}