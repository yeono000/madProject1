package com.yeono.madproject1.ui.game

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.ViewModel

data class GameDataModel(
    val name: String,
    val number: String,
    val photoBitmap: Bitmap?,
) : ViewModel(), Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readParcelable(Bitmap::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(number)
        parcel.writeParcelable(photoBitmap, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameDataModel> {
        override fun createFromParcel(parcel: Parcel): GameDataModel {
            return GameDataModel(parcel)
        }

        override fun newArray(size: Int): Array<GameDataModel?> {
            return arrayOfNulls(size)
        }
        override fun toString(): String {
            return "ContactDataModel(name='$this.name', phoneNumber='$this.phoneNumber', photo=$this.photo)"
        }
    }
}