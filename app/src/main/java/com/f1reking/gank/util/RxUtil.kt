package com.f1reking.gank.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * @author: huangyh
 * @date: 2018/1/10 00:22
 * @desc:
 */
class RxUtil {

    companion object {

        fun saveImageAndGetPathObservable(context: Context,
                                          url: String,
                                          title: String): Observable<Uri> {
            return Observable.create(ObservableOnSubscribe<Bitmap> { e ->
                var bitmap: Bitmap? = null
                Glide.with(context).asBitmap().load(url).into(
                    object : SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        override fun onResourceReady(resource: Bitmap?,
                                                     transition: Transition<in Bitmap>?) {
                            bitmap = resource
                        }
                    })
                if (bitmap == null) {
                    e.onError(Exception("无法下载到图片"))
                }
                bitmap?.let { e.onNext(it) }
                e.onComplete()
            }).flatMap({ bitmap ->
                val appDir = File(Environment.getExternalStorageDirectory(), "Mr.Gank")
                if (!appDir.exists()) {
                    appDir.mkdir()
                }
                val fileName = title.replace('/', '-') + ".jpg"
                val file = File(appDir, fileName)
                try {
                    val outputStream = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.flush()
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                val uri = Uri.fromFile(file) // 通知图库更新
                val scannerIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)
                context.sendBroadcast(scannerIntent)
                Observable.just(uri)
            }).subscribeOn(Schedulers.io())
        }
    }
}


