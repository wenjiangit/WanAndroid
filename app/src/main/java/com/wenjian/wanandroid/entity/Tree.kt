package com.wenjian.wanandroid.entity

import android.os.Parcel
import android.os.Parcelable


/**
 * Description ${name}
 *
 *
 * Date 2018/9/8
 *
 * @author wenjianes@163.com
 */


data class TreeEntry(
        val children: List<Children> = emptyList(),
        val courseId: Int,
        val id: Int,
        val name: String,
        val order: Int,
        val parentChapterId: Int,
        val visible: Int
) {
    data class Children(
            val children: List<Any>,
            val courseId: Int,
            val id: Int,
            val name: String,
            val order: Int,
            val parentChapterId: Int,
            val visible: Int
    )
}


data class SubTree(val id: Int, val name: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString() ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SubTree> {
        override fun createFromParcel(parcel: Parcel): SubTree {
            return SubTree(parcel)
        }

        override fun newArray(size: Int): Array<SubTree?> {
            return arrayOfNulls(size)
        }
    }
}