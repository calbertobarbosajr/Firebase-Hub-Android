package com.calberto_barbosa_jr.firebasehub.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.calberto_barbosa_jr.firebasehub.R
import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityStorageUploadBinding
import com.calberto_barbosa_jr.firebasehub.helper.DialogManager
import com.calberto_barbosa_jr.firebasehub.helper.PermissionManager
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.calberto_barbosa_jr.firebasehub.storage.UIState
import com.calberto_barbosa_jr.firebasehub.viewmodel.StorageUploadViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.activity.viewModels
import com.calberto_barbosa_jr.firebasehub.storage.FirebaseStoragePathProvider
import com.calberto_barbosa_jr.firebasehub.storage.StoragePathProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@AndroidEntryPoint
class StorageUploadActivity : AppCompatActivity() {

    //==================== PERMISSIONS =========================================================
    private lateinit var permissionManager: PermissionManager

    private val storagePermissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val storagePermissions33 = arrayOf(
        Manifest.permission.READ_MEDIA_VIDEO,
        Manifest.permission.READ_MEDIA_IMAGES
    )
    //=============================================================================================

    private lateinit var binding: ActivityStorageUploadBinding
    private val viewModel: StorageUploadViewModel by viewModels()

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.onImageSelected(it)
        } ?: showToast("Failed to select an image")
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            viewModel.onImageCaptured()
        } else {
            showToast("Failed to capture the photo")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPermissions()
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.apply {
            showImage1.setOnClickListener { onImageClicked(showImage1) }
            showImage2.setOnClickListener { onImageClicked(showImage2) }
            showImage3.setOnClickListener { onImageClicked(showImage3) }
            uploadButton.setOnClickListener {
                val user = Firebase.auth.currentUser
                user?.let {
                    val pathProvider = createPathProvider(it.uid)
                    viewModel.uploadImages(pathProvider)
                } ?: showToast("User not authenticated")
            }
        }
    }

    private fun onImageClicked(imageView: ImageView) {
        viewModel.onImageSelectedForDisplay(imageView)
        showCameraGalleryDialog(
            onCameraSelected = {
                val user = Firebase.auth.currentUser
                user?.let {
                    val pathProvider = createPathProvider(it.uid)
                    viewModel.openCamera(cameraLauncher, pathProvider)
                } ?: showToast("User not authenticated")
            },
            onGallerySelected = { galleryLauncher.launch("image/*") }
        )
    }

    private fun showCameraGalleryDialog(onCameraSelected: () -> Unit, onGallerySelected: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle("Choose an option")
            .setMessage("Would you like to take a photo or select from the gallery?")
            .setPositiveButton("Camera") { _, _ -> onCameraSelected() }
            .setNegativeButton("Gallery") { _, _ -> onGallerySelected() }
            .show()
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is UIState.ImageLoaded -> displayImage(state.uri)
                is UIState.UploadSuccess -> showSuccessDialog(state.imageUrl)
                is UIState.Error -> showToast(state.message)
                else -> {}
            }
        }
    }

    private fun displayImage(uri: Uri) {
        viewModel.selectedImageView?.let {
            Glide.with(this)
                .load(uri)
                .error(R.drawable.ic_error)
                .into(it)
        }
    }

    private fun showSuccessDialog(imageUrl: String) {
        AlertDialog.Builder(this)
            .setTitle("Upload Successful")
            .setMessage("Image URL: $imageUrl")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun createPathProvider(uid: String): StoragePathProvider {
        return FirebaseStoragePathProvider("images/$uid")
    }




















    //==================== PERMISSIONS =========================================================
    private fun setupPermissions() {
        permissionManager = PermissionManager(this)
        val requiredPermissions = getRequiredPermissions()

        if (permissionManager.hasPermissions(requiredPermissions)) {
            proceedWithActivity() // Continua com o fluxo da atividade
        } else {
            permissionManager.requestPermissions(requiredPermissions)
        }
    }

    private fun getRequiredPermissions(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            storagePermissions33
        } else {
            storagePermissions
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager.handlePermissionsResult(
            requestCode,
            permissions,
            grantResults,
            onAllGranted = { proceedWithActivity() },
            onPermissionDenied = { showPermissionDeniedAlert() }
        )
    }

    private fun showPermissionDeniedAlert() {
        DialogManager.showPermissionDeniedDialog(this) { finish() }
    }

    private fun proceedWithActivity() {
        // Lógica para continuar o fluxo da atividade
    }
}