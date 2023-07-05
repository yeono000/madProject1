package com.yeono.madproject1.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayModel : ViewModel() {

//    var id : String = "id"
//    var name: String = "yeono"
    private var _playerIds = MutableLiveData<Array<String>>().apply {
        value = arrayOf("1", "2", "3", "4")
    }
    private var _playerNames = MutableLiveData<Array<String>>().apply {
        value = arrayOf("연오","지민","선규","근원")
    }
    private val _numberOfPlayer = MutableLiveData<Int>().apply {
        value = 4
    }
    private val _cards = MutableLiveData<Array<Int>>().apply {
        value = arrayOf(4,6,3,1,1,2,8,3,1,4,4,5,5,6,1,1)
    }
    private val _turn = MutableLiveData<Int>().apply {
        value = 0
    }
    private val _index = MutableLiveData<Int>().apply {
        value = 0
    }
    private val _nowPlayer = MutableLiveData<PlayerModel>().apply {
        value = null
    }
    private val _gameEnd = MutableLiveData<Boolean>().apply {
        value = false
    }
    private var loser : MutableList<Int> = mutableListOf<Int>()

    val playerIds : LiveData<Array<String>> = _playerIds!!
    val playerNames : LiveData<Array<String>> = _playerNames!!
    val numberOfPlayer: LiveData<Int> = _numberOfPlayer!!
    val cards : LiveData<Array<Int>> = _cards!!
    val turn : LiveData<Int> = _turn!!
    val index : LiveData<Int> = _index!!
    val gameEnd : LiveData<Boolean> = _gameEnd!!
    var nowPlayer: LiveData<PlayerModel> = _nowPlayer!!

    fun setTurn(){
        _turn.value = _turn.value?.plus(1)
        _index.value = _index.value?.plus(1)
    }

    fun setPlayerIds(list: List<String>){
        _playerIds.value = list.toTypedArray()
        _numberOfPlayer.value = playerIds.value!!.size
    }

    fun setPlayerNames(list: List<String>){
        _playerNames.value = list.toTypedArray()
    }

    fun setCards(array: Array<Int>){
        _cards.value = array ?: arrayOf()
    }

    fun setNowPlayer(model: PlayerModel){
        _nowPlayer.value = model
    }
    fun getCard(): Int{
        return cards.value?.get(_turn.value ?: 0)?:0
    }

    fun addLoser(position: Int){
        loser.add(position)
        Log.d("loser", position.toString()+" "+loser.size+" "+_gameEnd.value)
        if(loser.size == numberOfPlayer.value!!-1){
            Log.d("loser", "in")
            _gameEnd.value = true
            Log.d("loser", "out")
        }
    }
    fun setGameEnd(boolean: Boolean){
        _gameEnd.value = boolean
    }
    fun playTurn(playerDataList: List<PlayerModel>, removeIdx: Int?){
        if(removeIdx == 0){
            Log.d("remove", "removeCard")
            nowPlayer.value!!.setCard(nowPlayer.value!!.newCard.value!!)
            nowPlayer.value!!.setNewCard(0)
        }else if(removeIdx == 1){
            Log.d("remove", "remove new Card")
            nowPlayer.value!!.setNewCard(0)
        }
        Log.d("loser", _index.value.toString()+" "+removeIdx.toString())
        if(gameEnd.value!!){
            return
        }
        val beforeIdx = index.value!! - 1
        while (index.value!! % numberOfPlayer.value!! in loser){
            _index.value = _index.value?.plus(1)
        }
        val idx = index.value!!
        if(idx < 15) {
            playerDataList[beforeIdx% numberOfPlayer.value!!].setShowCard(false)
            var turnPlayer = playerDataList[idx % numberOfPlayer.value!!]
            setNowPlayer(turnPlayer)
            turnPlayer.setProtected(false)
            turnPlayer.setNewCard(cards.value!!.get(idx))
            turnPlayer.setShowCard(true)
            setTurn()
        }
    }
}