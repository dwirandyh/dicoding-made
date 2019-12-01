package com.dwirandyh.movieapp

import android.os.Parcel
import android.os.Parcelable

data class Movie(
    val photo: Int,
    val name: String,
    val director: String,
    val time: Int,
    val rating: Float,
    val ratingStar: Float,
    val description: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(photo)
        parcel.writeString(name)
        parcel.writeString(director)
        parcel.writeInt(time)
        parcel.writeFloat(rating)
        parcel.writeFloat(ratingStar)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}