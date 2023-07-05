package com.yeono.madproject1.ui.game

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.renderscript.ScriptGroup.Binding
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.yeono.madproject1.MainActivity
import com.yeono.madproject1.R
import com.yeono.madproject1.databinding.FragmentGameBinding
import com.yeono.madproject1.databinding.FragmentPlayBinding
import com.yeono.madproject1.databinding.InfoModalBinding
import com.yeono.madproject1.databinding.ItemPlayerBinding
import kotlin.concurrent.thread

class PlayerListAdapter(private val playModel: PlayModel, private val lifecycleOwner: LifecycleOwner, private val dataList: List<PlayerModel>,
private val context: Context) : BaseAdapter() {
    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    var killSol: Int = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ItemPlayerBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        val playerModel = dataList[position]
        binding.apply {
            viewModel = playerModel
        }
        if(convertView == null){
            Log.d("turn", convertView.toString())
            playerModel.wins.observe(lifecycleOwner){ data ->
                notifyDataSetChanged()
            }
            playerModel.showCard.observe(lifecycleOwner){ data ->
                if(data != true){
                    playerModel.setSelectedCard(-1)
                    binding.card.setBackgroundResource(R.drawable.shadow_red)
                    binding.cardNew.setBackgroundResource(R.drawable.shadow_red)
                    changeCardImage(playerModel.newCard.value?: 0, binding.cardNew, true)
                    changeCardImage(playerModel.card.value?: 0, binding.card, true)
                    binding.card.setOnClickListener(null)
                    binding.cardNew.setOnClickListener(null)
                    binding.arrow.visibility = View.GONE
                }
                else {
                    binding.arrow.visibility = View.VISIBLE
                    binding.card.setBackgroundResource(R.drawable.shadow)
                    binding.cardNew.setBackgroundResource(R.drawable.shadow)
                    if (playerModel.newCard.value == 7 && playerModel.card.value == 6 || playerModel.card.value == 5) {
                        playerModel.setSelectedCard(1)
                    }
                    else if (playerModel.card.value == 7 && playerModel.newCard.value == 6 || playerModel.newCard.value == 5) {
                        playerModel.setSelectedCard(0)
                    }
                    else {
                        binding.card.setOnClickListener {
                            playerModel.setSelectedCard(0)
                            Log.d("fun", playerModel.name+"'s card ready "+data.toString())
                        }
                        binding.cardNew.setOnClickListener {
                            playerModel.setSelectedCard(1)
                            Log.d("fun", playerModel.name+"'s card ready "+data.toString())
                        }
                    }
                    changeCardImage(playerModel.newCard.value ?: 0, binding.cardNew, false)
                    changeCardImage(playerModel.card.value ?: 0, binding.card, false)
                }
            }
            playerModel.newCard.observe(lifecycleOwner){newCard ->
                changeCardImage(newCard, binding.cardNew, !(playerModel.showCard.value!!))
                changeCardImage(playerModel.card.value?:0, binding.card, !(playerModel.showCard.value!!))
                binding.playerName.text = playerModel.getText()
            }
            playerModel.card.observe(lifecycleOwner){card ->
                binding.playerName.text = playerModel.getText()
            }
            playerModel.seletedCard.observe(lifecycleOwner){card ->
                if(card == -1){
                    binding.arrow.setImageResource(R.drawable.baseline_double_arrow_grey24)
                    binding.card.setPadding(0, 0, 0, 0)
                    binding.cardNew.setPadding(0, 0, 0, 0)
                    binding.card.setOnClickListener(null)
                } else if(card == 0){
                    binding.card.setPadding(10, 10, 10, 10)
                    binding.cardNew.setPadding(0, 0, 0, 0)
                    if(playerModel.showCard.value == true) {
                        binding.arrow.setImageResource(R.drawable.baseline_double_arrow_24)
                        functionStart(binding, position, playerModel.card.value!!,0)
                    }
                    else{
                        Log.d("fun", "setting red and rece"+position.toString())
                        functionReceiver(binding, playerModel)
                    }
                } else if(card == 1){
                    binding.cardNew.setPadding(10, 10, 10, 10)
                    binding.card.setPadding(0, 0, 0, 0)
                    binding.arrow.setImageResource(R.drawable.baseline_double_arrow_24)
                    functionStart(binding, position, playerModel.newCard.value!!, 1)
                }
            }
        }
        return binding.root
    }

    private fun changeCardImage(card: Int, view: ImageView, hide: Boolean){
        if (card == 0) {
            view.visibility = View.GONE
        }else{
            view.visibility = View.VISIBLE
        }
        if(hide){
            view.setImageResource(R.drawable.card_image)
        } else if (card == 1 ) {
            view.setImageResource(R.drawable.card1)
        } else if (card == 2) {
            view.setImageResource(R.drawable.card2)
        } else if (card == 3) {
            view.setImageResource(R.drawable.card3)
        } else if (card == 4) {
            view.setImageResource(R.drawable.card4)
        } else if (card == 5) {
            view.setImageResource(R.drawable.card5)
        } else if (card == 6) {
            view.setImageResource(R.drawable.card6)
        } else if (card == 7) {
            view.setImageResource(R.drawable.card7)
        } else if (card == 8) {
            view.setImageResource(R.drawable.card8)
        }
    }
    private fun functionStart(binding: ItemPlayerBinding, position: Int, cardNum: Int, select: Int){
        Log.d("loser", "select - "+select.toString())
        val playerModel = dataList[position]
        binding.arrow.setOnClickListener {
            binding.arrow.visibility = View.GONE
            if(cardNum == 1){
                binding.card.setOnClickListener(null)
                binding.cardNew.setOnClickListener(null)
                val dialog = Dialog(context)
                dialog.setContentView(R.layout.button_modal)
                val clickListener = View.OnClickListener { view ->
                    var value = when (view.id) {
                        R.id.btn1 -> 1
                        R.id.btn2 -> 2
                        R.id.btn3 -> 3
                        R.id.btn4 -> 4
                        R.id.btn5 -> 5
                        R.id.btn6 -> 6
                        R.id.btn7 -> 7
                        R.id.btn8 -> 8
                        else -> 0
                    }
                    view.setOnClickListener {
                        dialog.hide()
                        killSol = value
                    }
                }
                val btnClose = dialog.findViewById<Button>(R.id.modal_button)
                val btn1 = dialog.findViewById<Button>(R.id.btn1).setOnClickListener(clickListener)
                val btn2 = dialog.findViewById<Button>(R.id.btn2).setOnClickListener(clickListener)
                val btn3 = dialog.findViewById<Button>(R.id.btn3).setOnClickListener(clickListener)
                val btn4 = dialog.findViewById<Button>(R.id.btn4).setOnClickListener(clickListener)
                val btn5 = dialog.findViewById<Button>(R.id.btn5).setOnClickListener(clickListener)
                val btn6 = dialog.findViewById<Button>(R.id.btn6).setOnClickListener(clickListener)
                val btn7 = dialog.findViewById<Button>(R.id.btn7).setOnClickListener(clickListener)
                val btn8 = dialog.findViewById<Button>(R.id.btn8).setOnClickListener(clickListener)
                dialog.show()
                binding.card.setOnClickListener(null)
                binding.cardNew.setOnClickListener(null)
                Log.d("fun", "arrowClick")
                for (i in dataList.indices) {
                    if (i != position) {
                        dataList[i].setSelectedCard(0)
                    }
                }
            }
            else if(cardNum == 2||cardNum == 3||cardNum == 5||cardNum == 6){
                binding.card.setOnClickListener(null)
                binding.cardNew.setOnClickListener(null)
                Log.d("fun", "arrowClick")
                for (i in dataList.indices) {
                    if (i != position) {
                        dataList[i].setSelectedCard(0)
                    }
                }
            }
            else if(cardNum == 5){
                binding.card.setOnClickListener(null)
                binding.cardNew.setOnClickListener(null)
                Log.d("fun", "arrowClick")
                for (i in dataList.indices) {
                    dataList[i].setSelectedCard(0)
                }
            }
            else if(cardNum == 4){
                if(select == 1){
                    changeCardImage(playerModel.newCard.value ?: 0, binding.cardNew, false)
                } else{
                    changeCardImage(playerModel.card.value ?: 0, binding.card, false)
                }
                playerModel.setProtected(true)
                Handler().postDelayed({
                    playModel.playTurn(dataList, select)
                }, 2000)
            }
            else if(cardNum == 7){
                if(select == 1){
                    changeCardImage(playerModel.newCard.value ?: 0, binding.cardNew, false)
                } else{
                    changeCardImage(playerModel.card.value ?: 0, binding.card, false)
                }
                Handler().postDelayed({
                    playModel.playTurn(dataList, select)
                }, 2000)
            }
            else if(cardNum == 8){
                if(select == 1){
                    changeCardImage(playerModel.newCard.value ?: 0, binding.cardNew, false)
                } else{
                    changeCardImage(playerModel.card.value ?: 0, binding.card, false)
                }
                Handler().postDelayed({
                    if(select == 1){
                        playerModel.setCard(0)
                    }else{
                        playerModel.setNewCard(0)
                    }
                    playModel.addLoser(position)
                    playModel.playTurn(dataList, select)
                }, 2000)
            }
        }
    }
    private fun functionReceiver(binding: ItemPlayerBinding, playerModel: PlayerModel){
        val nowPlayer = playModel.nowPlayer.value!!
        val selecedIdx = nowPlayer.seletedCard.value!!
        val selecedCard = if(selecedIdx==0) nowPlayer.card.value!! else nowPlayer.newCard.value!!
        val unSelectedCard = if(selecedIdx==1) nowPlayer.card.value!! else nowPlayer.newCard.value!!

        binding.card.setOnClickListener {
            var idx = playModel.nowPlayer.value!!.seletedCard.value
            for (i in dataList) {
                i.setSelectedCard(-1)
            }
            if(playerModel.protected.value!!){
                Handler().postDelayed({
                    playModel.playTurn(dataList, idx)
                }, 2000)
            }
            if (selecedCard == 1 && !playerModel.protected.value!!) {
                changeCardImage(playerModel.card.value ?: 0, binding.card, false)
                Handler().postDelayed({
                    if (playerModel.card.value!! == killSol) {
                        playerModel.setCard(0)
                        playModel.addLoser(playerModel.playerNumber.value!!)
                    }
                    killSol = 0
                    changeCardImage(playerModel.card.value ?: 0, binding.card, true)
                    playModel.playTurn(dataList, idx)
                }, 2000)
            }
            else if (selecedCard == 2 && !playerModel.protected.value!!) {
                changeCardImage(playerModel.card.value ?: 0, binding.card, false)
                Handler().postDelayed({
                    changeCardImage(playerModel.card.value ?: 0, binding.card, true)
                    playModel.playTurn(dataList, idx)
                }, 2000)
            }
            else if (selecedCard == 3 && !playerModel.protected.value!!) {
                changeCardImage(playerModel.card.value ?: 0, binding.card, false)
                Handler().postDelayed({
                    if (unSelectedCard < playerModel.card.value!!) {
                        if (nowPlayer.seletedCard.value!! == 0) {
                            nowPlayer.setNewCard(0)
                        } else {
                            nowPlayer.setCard(0)
                        }
                        nowPlayer.setNewCard(0)
                        playModel.addLoser(nowPlayer.playerNumber.value!!)
                    } else if (unSelectedCard > playerModel.card.value!!) {
                        playerModel.setCard(0)
                        playModel.addLoser(playerModel.playerNumber.value!!)
                    }
                    changeCardImage(playerModel.card.value ?: 0, binding.card, true)
                    playModel.playTurn(dataList, idx)
                }, 2000)
            }
            else if (selecedCard == 5 && !playerModel.protected.value!!) {
                changeCardImage(playerModel.card.value ?: 0, binding.card, false)
                Handler().postDelayed({
                    changeCardImage(playerModel.card.value ?: 0, binding.card, true)
                    playerModel.setCard(playModel.getCard())
                    playModel.playTurn(dataList, idx)
                }, 2000)
            }
            else if (selecedCard == 6 && !playerModel.protected.value!!) {
                changeCardImage(playerModel.card.value ?: 0, binding.card, false)
                Handler().postDelayed({
                    changeCardImage(playerModel.card.value ?: 0, binding.card, true)
                    val tmp = playerModel.card.value!!
                    playerModel.setCard(unSelectedCard)
                    if(idx == 0){
                        nowPlayer.setNewCard(tmp)
                    }
                    else{
                        nowPlayer.setCard(tmp)
                    }
                    playModel.playTurn(dataList, idx)
                }, 2000)
            }
        }
    }
}
