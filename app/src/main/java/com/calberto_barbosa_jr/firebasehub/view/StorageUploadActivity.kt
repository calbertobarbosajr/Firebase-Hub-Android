package com.calberto_barbosa_jr.firebasehub.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calberto_barbosa_jr.firebasehub.R
import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityStorageUploadBinding
import com.calberto_barbosa_jr.firebasehub.helper.PermissionUtil
import com.calberto_barbosa_jr.firebasehub.repository.StorageUploadRepository
import com.calberto_barbosa_jr.firebasehub.viewmodel.StorageUploadViewModel
import com.calberto_barbosa_jr.firebasehub.viewmodel.StorageUploadViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class StorageUploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStorageUploadBinding
    private val imageUris = mutableListOf<Uri>()
    private var uriImage: Uri? = null
    private lateinit var showImage1: ImageView
    private lateinit var showImage2: ImageView
    private lateinit var showImage3: ImageView
    private var selectedImageView: ImageView? = null

    private val storageViewModel: StorageUploadViewModel by viewModels {
        StorageUploadViewModelFactory(
            StorageUploadRepository(FirebaseStorage.getInstance(), applicationContext)
        )
    }

    private val storagePermissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private val storagePermissions33 = arrayOf(
        Manifest.permission.READ_MEDIA_VIDEO,
        Manifest.permission.READ_MEDIA_IMAGES
    )

    private fun permissions(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            storagePermissions33
        } else {
            storagePermissions
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStorageUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupObservers()
        initializeViews()
        requestPermissions()
    }

    private fun setupObservers() {
        storageViewModel.uploadStatus.observe(this, { uploadStatus ->
            if (uploadStatus) {
                showToast("Upload Success")
                finish()
            } else {
                showToast("Error when uploading")
            }
        })
    }

    private fun initializeViews() {
        showImage1 = findViewById(R.id.showImage1)
        showImage2 = findViewById(R.id.showImage2)
        showImage3 = findViewById(R.id.showImage3)
    }

    private fun requestPermissions() {
        val requiredPermissions = permissions()
        val permissionsHelper = PermissionUtil()
        permissionsHelper.validatePermissions(
            requiredPermissions,
            this,
            PermissionUtil.REQUEST_PERMISSIONS_CODE
        )
    }

    fun showImage1(view: View) {
        selectedImageView = showImage1
        showCameraGalleryDialog()
    }

    fun showImage2(view: View) {
        selectedImageView = showImage2
        showCameraGalleryDialog()
    }

    fun showImage3(view: View) {
        selectedImageView = showImage3
        showCameraGalleryDialog()
    }

    fun buttonUpload(view: View) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uid: String = user.uid
            storageViewModel.uploadImages(uid, imageUris)
        }
    }

    private fun showCameraGalleryDialog() {
        AlertDialog.Builder(this)
            .setMessage("Camera or Gallery?")
            .setTitle("Choose!")
            .setCancelable(false)
            .setPositiveButton("Camera") { _, _ ->
                itemCamera()
            }
            .setNegativeButton("Gallery") { _, _ ->
                obterImagemGaleria()
            }
            .create()
            .show()
    }

    private fun itemCamera() {
        if (ContextCompat.checkSelfPermission(
                baseContext,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            obterImagemCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_CAMERA_CODE
            )
        }
    }

    private fun obterImagemGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        getContent.launch(intent)
    }

    private fun obterImagemCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val nomeImagem = "Name_${System.currentTimeMillis()}.jpg"
        val file = File(diretorio, nomeImagem)
        val autorizacao = "com.calberto_barbosa_jr.firebasehub.fileprovider" // Certifique-se de usar o mesmo nome configurado no seu Manifest.
        val uriImagem = FileProvider.getUriForFile(baseContext, autorizacao, file)
        uriImage = uriImagem
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage)
        getCameraImage.launch(intent)
    }


    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                if (data != null) {
                    uriImage = data.data
                    loadImageIntoImageView(selectedImageView)
                } else {
                    showToast("Failed to select image")
                }
            }
        }

    private val getCameraImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                loadImageIntoImageView(selectedImageView)
            } else {
                showToast("Failed to take photo")
            }
        }

    private fun loadImageIntoImageView(imageView: ImageView?) {
        uriImage?.let {
            imageView?.let { imageView ->
                Glide.with(baseContext)
                    .asBitmap()
                    .load(it)
                    .listener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            showToast("Error loading image")
                            return false
                        }

                        override fun onResourceReady(
                            resource: Bitmap?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }
                    })
                    .into(imageView)
                imageUris.add(it)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val PERMISSION_CAMERA_CODE = 1001
    }

    //===============================================================================================
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (arePermissionsDenied(grantResults)) {
            showPermissionDeniedAlert()
        }
    }

    private fun arePermissionsDenied(grantResults: IntArray): Boolean {
        return grantResults.any { it == PackageManager.PERMISSION_DENIED }
    }

    private fun showPermissionDeniedAlert() {
        android.app.AlertDialog.Builder(this)
            .setTitle("Permissions Denied")
            .setMessage("To use the app, you need to accept the required permissions.")
            .setCancelable(false)
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, _ -> finish() })
            .create()
            .show()
    }

}