package com.wenjian.wanandroid.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * Description: WebModel
 * Date: 2018/9/21
 *
 * @author jian.wen@ubtrobot.com
 */
data class WebModel(val id: Int, val link: String?, val collect: Boolean) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readByte() != 0.toByte())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(link)
        parcel.writeByte(if (collect) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WebModel> {
        override fun createFromParcel(parcel: Parcel): WebModel {
            return WebModel(parcel)
        }

        override fun newArray(size: Int): Array<WebModel?> {
            return arrayOfNulls(size)
        }
    }
}