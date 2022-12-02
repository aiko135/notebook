package ktepin.penzasoft.dairy.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionManager(
    private val activity: Activity,
) {
    val RUNTIME_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )

    fun arePermissionsGranted(): Boolean {
        var res = true;
        RUNTIME_PERMISSIONS.forEach{
            if (ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED)
                res = false;
        }
        return res
    }

    fun requestPermissions() {
        var permsToRequest = mutableListOf<String>()
        RUNTIME_PERMISSIONS.forEach {
            if (ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED) {
                permsToRequest += it
            }
        }
        ActivityCompat.requestPermissions(activity, permsToRequest.toTypedArray(), 1)
    }
}