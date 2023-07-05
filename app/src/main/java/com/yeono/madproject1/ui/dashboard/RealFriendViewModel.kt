package com.yeono.madproject1.ui.dashboard

import android.os.Parcel
import android.os.Parcelable

data class RealFriendViewModel(
    val imageList: MutableList<Int>
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createIntArray()?.toMutableList() ?: mutableListOf())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeIntArray(imageList.toIntArray())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageDataModel> {
        override fun createFromParcel(parcel: Parcel): ImageDataModel {
            return ImageDataModel(parcel)
        }

        override fun newArray(size: Int): Array<ImageDataModel?> {
            return arrayOfNulls(size)
        }
    }
}