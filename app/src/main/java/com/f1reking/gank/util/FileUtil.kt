package com.f1reking.gank.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.view.View
import android.widget.Toast
import com.f1reking.gank.R
import com.f1reking.gank.R.string
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

/**
 * @author: huangyh
 * @date: 2018/1/17 13:39
 * @desc:
 */
class FileUtil {

    companion object {

        fun saveImageToGallery(context: Context,
                               view: View,
                               bitmap: Bitmap,
                               title: String) {
            var save: Boolean
            val appDir = File(Environment.getExternalStorageDirectory(), "Mr.Gank")
            if (!appDir.exists()) {
                appDir.mkdir()
            }
            val fileName = title + ".jpg"
            val file = File(appDir, fileName)

            try {
                val fos = FileOutputStream(file)
                save = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
            } catch (e: FileNotFoundException) {
                save = false
                e.printStackTrace()
            } catch (e: IOException) {
                save = false
                e.printStackTrace()
            } //通知图库更新
            context.sendBroadcast(
                Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.path)))
            if (save) {
                Snackbar.make(view, context.getString(string.save_image_success),
                    Snackbar.LENGTH_LONG).setActionTextColor(
                    ContextCompat.getColor(context, R.color.white)).setAction(
                    context.getString(string.open)) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.addCategory(Intent.CATEGORY_DEFAULT)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                        val contentUri = FileProvider.getUriForFile(context,
                            context.packageName + ".fileProvider", file)
                        intent.setDataAndType(contentUri, "image/*")
                    } else {
                        intent.setDataAndType(Uri.fromFile(file), "image/*")
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    context.startActivity(intent)
                }.show()
            } else {
                Toast.makeText(context, context.getString(string.save_image_fail),
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}


