package com.yeono.madproject1.ui.game

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class PlayerDataModel(
    val id: String,
    val name: String,
    val playerNumber: LiveData<Int>,
    val wins: LiveData<Int>,
    val newCard: LiveData<Int>,
    val card: LiveData<Int>,
) : ViewModel(), Parcelable {
    constructor(
        id: String,
        name: String,
        playerNumber: Int,
    ) : this(
        id,
        name,
        MutableLiveData(playerNumber),
        MutableLiveData(0),
        MutableLiveData(0),
        MutableLiveData(0)
    )
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
         MutableLiveData(parcel.readInt()),
         MutableLiveData(parcel.readInt()),
        MutableLiveData(parcel.readInt()),
        MutableLiveData(parcel.readInt())
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeInt(playerNumber.value!!)
        parcel.writeInt(wins.value!!)
        parcel.writeInt(newCard.value!!)
        parcel.writeInt(card.value!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlayerDataModel> {
        override fun createFromParcel(parcel: Parcel): PlayerDataModel {
            return PlayerDataModel(parcel)
        }

        override fun newArray(size: Int): Array<PlayerDataModel?> {
            return arrayOfNulls(size)
        }
    }
}