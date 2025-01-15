package com.calberto_barbosa_jr.firebasehub.storage

import android.net.Uri

sealed class UIState {
    data class ImageLoaded(val uri: Uri) : UIState()
    data class UploadSuccess(val imageUrl: String) : UIState()
    data class Error(val message: String) : UIState()
}
