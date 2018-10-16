package com.wenjian.wanandroid.utils

import java.io.File

/**
 * Description: FileUtil
 * Date: 2018/10/16
 *
 * @author jian.wen@ubtrobot.com
 */
object FileUtil {

    fun deleteFile(file: File) {
        if (file.isDirectory) {
            for (f in file.listFiles()) {
                deleteFile(f)
            }
        } else {
            file.delete()
        }
    }

    private fun countDirectorySize(file: File): Long {
        var totalCount = 0L
        for (f in file.listFiles()) {
            totalCount += if (f.isDirectory) {
                countDirectorySize(f)
            } else {
                f.length()
            }
        }
        return totalCount
    }

    private fun countSize(file: File): Long {
        var totalCount = 0L
        totalCount += if (file.isDirectory) {
            countDirectorySize(file)
        } else {
            file.length()
        }
        return totalCount
    }

    fun getFormatSize(file: File): String {
        val size = countSize(file)
        return if (size / (1024 * 1024) > 1) {//大于1M,转化为M
            (size / (1024 * 1024)).toString() + "M"
        } else {//否者为KB
            (size / 1024).toString() + "KB"
        }
    }

}