package com.calberto_barbosa_jr.firebasehub.helper

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionManager(private val activity: AppCompatActivity) {

    companion object {
        const val REQUEST_PERMISSIONS_CODE = 1
    }

    fun hasPermissions(permissions: Array<String>): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestPermissions(permissions: Array<String>) {
        if (permissions.isNotEmpty()) { // Adicionado para evitar array vazio
            ActivityCompat.requestPermissions(activity, permissions, REQUEST_PERMISSIONS_CODE)
        }
    }

    fun handlePermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
        onAllGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                onAllGranted()
            } else {
                onPermissionDenied()
            }
        }
    }

}

