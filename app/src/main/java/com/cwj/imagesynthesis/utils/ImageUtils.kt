package com.yun.utils.image

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log.i
import android.view.View
import android.widget.Toast
import com.cwj.imagesynthesis.utils.FileUtils
import com.cwj.imagesynthesis.utils.LogUtlis

import java.io.*
import java.net.HttpURLConnection
import java.net.URL

/**
 * 图片工具类
 */
object ImageUtils {

    /**
     * 将view 转换为bitmap
     */
    fun saveBitmapFromView(view: View): Bitmap {
        val bmp: Bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        view.draw(c)
        return bmp
    }

    /**
     * 保存图片到图库
     *
     * @param context
     * @param bmp
     */
    fun saveImageToGallery(context: Context, bmp: Bitmap): Boolean {
        val file = saveImageToFile(context, bmp, System.currentTimeMillis().toString() + ".jpg")
            ?: return false
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(
                context.contentResolver,
                file.absolutePath, file.name, null
            )
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        // 最后通知图库更新
        context.sendBroadcast(
            Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + file.absolutePath)
            )
        )
        return true
    }


    /**
     * 将图片保存问文件
     */
    fun saveImageToFile(context: Context, bmp: Bitmap, fileName: String?): File? {

        val file = FileUtils.createImageFile(fileName)
        try {
            val bos = BufferedOutputStream(FileOutputStream(file))
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, bos)
            bos.flush()
            bos.close()
            Toast.makeText(context, "图片保存成功！", Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return null
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return file


    }

    //================================================================图片保存为文件============================================================================
    /**
     * 将图片地址保存为文件
     */
    fun saveImageUrlToFile(image: String): File? {
        LogUtlis.d("图片地址:$image")
        try {
            val file = FileUtils.createImageFile()
            val bitmap: Bitmap?
            val url = URL(image)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            val `is`: InputStream?
            `is` = conn.inputStream
            bitmap = BitmapFactory.decodeStream(`is`)
            val outStream: FileOutputStream
            outStream = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, outStream)
            outStream.flush()
            outStream.close()
            return file
        } catch (e: Exception) {
            LogUtlis.e("保存图片异常" + e.toString())
        }
        return null

    }

    /**
     * 将图片资源保存为bitmap
     */
    fun saveImageUrlToBitmap(image: String): Bitmap? {
        LogUtlis.d("图片地址:$image")
        try {
            val bitmap: Bitmap?
            val url = URL(image)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            val `is`: InputStream?
            `is` = conn.inputStream
            bitmap = BitmapFactory.decodeStream(`is`)
            return bitmap
        } catch (e: Exception) {
            LogUtlis.e("保存图片异常" + e.toString())
        }
        return null

    }


}