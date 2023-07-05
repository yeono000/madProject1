package com.yeono.madproject1.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yeono.madproject1.R

class PlayerModel : ViewModel() {

    var id : String = "id"
    var name: String = "yeono"
    private var _playerNumber = MutableLiveData<Int>().apply {
        value = 0
    }
    private val _wins = MutableLiveData<Int>().apply {
        value = 0
    }
    private val _newCard = MutableLiveData<Int>().apply {
        value = 0
    }
    private val _card = MutableLiveData<Int>().apply {
        value = 1
    }
    private val _showCard = MutableLiveData<Boolean>().apply {
        value = false
    }
    private val _selectedCard = MutableLiveData<Int>().apply {
        value = -1
    }
    private val _protected = MutableLiveData<Boolean>().apply {
        value = false
    }


    val playerNumber: LiveData<Int> = _playerNumber
    val wins: LiveData<Int> = _wins
    val newCard: LiveData<Int> = _newCard
    val card: LiveData<Int> = _card
    val seletedCard: LiveData<Int> = _selectedCard
    val showCard: LiveData<Boolean> = _showCard
    val protected: LiveData<Boolean> = _protected

    fun setPlayer(id: String, name: String) {
        this.id = id
        this.name = name
    }
    fun setPlayerNumber(number: Int){
        _playerNumber.value = number
    }

    fun setShowCard(boolean: Boolean){
        _showCard.value = boolean
    }
    fun setProtected(boolean: Boolean){
        _protected.value = boolean
    }
    fun setSelectedCard(number: Int){
        _selectedCard.value = number
    }
    fun setWins(){
        _wins.value = (_wins.value)!!.plus(1)
    }
    fun setCard(card: Int){
        _card.value = card
    }

    fun setNewCard(card: Int){
        _newCard.value = card
    }
    fun getText():String{
        return "${name} (${card.value}) (${newCard.value}) -${protected.value.toString()}"
    }
}