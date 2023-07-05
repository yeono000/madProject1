package com.yeono.madproject1.ui.dashboard
import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.ViewModel

data class FriendsModel(
    val name: String,
    val number: String,
) : ViewModel(), Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(number)
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
        override fun toString(): String {
            return "ContactDataModel(name='$this.name', phoneNumber='$this.phoneNumber')"
        }
    }
}