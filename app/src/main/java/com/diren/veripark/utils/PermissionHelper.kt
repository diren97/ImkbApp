package com.diren.veripark.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

//izin yardımcısı
object PermissionHelper {

    val locationPermissions =
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    val cameraPermission =
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

    val callPermission =
        listOf(Manifest.permission.CALL_PHONE)
    val contactPermission =
        listOf(Manifest.permission.READ_CONTACTS)

    val wifiPermission =
        listOf(
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE
        )

    fun checkWifiPermissions(context: Context) =
        checkPermissions(context, wifiPermission)

    fun checkLocationPermission(context: Context): Boolean =
        checkPermissions(context, locationPermissions)

    fun checkCameraPermissions(context: Context) =
        checkPermissions(context, cameraPermission)

    fun checkCallPermissions(context: Context) =
        checkPermissions(context, callPermission)

    fun checkContactPermissions(context: Context) =
        checkPermissions(context, contactPermission)

    private fun checkPermissions(
        context: Context,
        permissions: List<String>
    ): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}