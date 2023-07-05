package com.yeono.madproject1.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yeono.madproject1.R
import com.yeono.madproject1.ui.game.PlayerModel

class UserModel : ViewModel() {

    private var _friendList = MutableLiveData<ArrayList<Int>>().apply {
        value = arrayListOf()
    }
    private var _userList = MutableLiveData<ArrayList<UserDto>>().apply {
        value = arrayListOf()
    }
    private var _playerList = MutableLiveData<ArrayList<UserDto>>().apply {
        value = arrayListOf()
    }
    var uid : String? = null
    var name : String? = "player"
    val friendList: LiveData<ArrayList<Int>> = _friendList!!
    val playerList: LiveData<ArrayList<UserDto>> = _playerList!!
    val userList: LiveData<ArrayList<UserDto>> = _userList!!

    fun setUserId(id:String) {
        uid = id
    }
    fun setUserName(value: String) {
        name = value
    }
    fun addUser(user :UserDto) {
        _userList.value!!.add(user)
    }
    fun addFriend(id: Int) {
        _friendList.value!!.add(id)
    }
    fun addPlayer(id: Int) {
        if(!(_userList.value!!.get(id) in _playerList.value!!)){
            _playerList.value!!.add(_userList.value!!.get(id))
        }
    }
}