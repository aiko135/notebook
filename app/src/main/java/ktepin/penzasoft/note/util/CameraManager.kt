package ktepin.penzasoft.note.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/*
* Заметка.
* Чтобы камера могла сохранить фото в getExternalFilesDir текущего приложения
* используется FileProvider (подкласс ContentProvider'а), его нотация AndroidManifest
* FileProvider.getUriForFile(..) используется для создания Uri по которому камера сможет передать картинку во external storage приложения
* */
class CameraManager(
    val ctx: Context
) {

    var file: File? = null

    fun getIntent():Intent?{
        var res: Intent? = null
        try {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? = ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            storageDir?.let {
                file = File(storageDir, "JPEG_${timeStamp}.jpg")
                val uri =  FileProvider.getUriForFile(
                    ctx,
                    "${ctx.applicationContext.packageName}.fileprovidercustom",
                    file!!
                )
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                takePictureIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                res = takePictureIntent
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return res
    }

    fun saveToGallery():Uri?{
        var res:Uri? = null
        file?.let {

            res = Uri.fromFile(file)
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            mediaScanIntent.data = res
            ctx.sendBroadcast(mediaScanIntent)
        }
        return res
    }
}