package com.calberto_barbosa_jr.firebasehub.helper

import android.content.Context
import androidx.appcompat.app.AlertDialog

object DialogManager {
    fun showPermissionDeniedDialog(context: Context, onPositiveAction: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle("Permissions Denied")
            .setMessage("To use the app, you need to accept the required permissions.")
            .setCancelable(false)
            .setPositiveButton("OK") { _, _ -> onPositiveAction() }
            .create()
            .show()
    }
}
