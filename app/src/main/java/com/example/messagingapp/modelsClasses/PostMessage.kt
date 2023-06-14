package com.example.messagingapp.modelsClasses

import android.os.Parcel
import android.os.Parcelable

data class PostMessage(
    val From: String?,
    val To: String?,
    val Body: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(From)
        parcel.writeString(To)
        parcel.writeString(Body)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostMessage> {
        override fun createFromParcel(parcel: Parcel): PostMessage {
            return PostMessage(parcel)
        }

        override fun newArray(size: Int): Array<PostMessage?> {
            return arrayOfNulls(size)
        }
    }
}