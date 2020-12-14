package com.spbstu.reversemarket.utils

import android.Manifest
import android.content.Context
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

object PermissionUtils {

    fun checkReadWriteStorage(context: Context, onComplete: (Boolean) -> Unit) {
        checkPermissions(
            listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), context, onComplete
        )
    }

    private fun checkPermissions(
        permissions: Collection<String>,
        context: Context,
        onComplete: ((Boolean) -> Unit)?
    ) {
        Dexter.withContext(context)
            .withPermissions(permissions)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    val allGranted = report?.areAllPermissionsGranted() ?: false
                    if (onComplete != null) {
                        if (allGranted) {
                            onComplete(true)
                        } else {
                            onComplete(false)
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .check()
    }

    private fun checkOnePermission(
        permission: String,
        context: Context,
        onComplete: (Boolean) -> Unit
    ) {
        Dexter.withContext(context)
            .withPermission(permission)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    onComplete(true)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    onComplete(false)
                }
            }).check()
    }
}
