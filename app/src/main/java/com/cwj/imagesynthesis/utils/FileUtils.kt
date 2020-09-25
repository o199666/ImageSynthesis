package com.cwj.imagesynthesis.utils

import android.os.Environment
import android.text.TextUtils
import java.io.File
import java.io.IOException

/**
 * 文件管理类
 */
object FileUtils {

    /**
     * 创建图片文件
     */
    fun createImageFile(): File {
        return createImageFile(System.currentTimeMillis().toString() + ".jpg")
    }

    fun createImageFile( fileName: String?): File {
        val appDir = File(Environment.getExternalStorageDirectory(), "DCIM")
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val file = File(appDir, fileName)
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return file

    }

}