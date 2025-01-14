package com.calberto_barbosa_jr.firebasehub.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.calberto_barbosa_jr.firebasehub.R
import androidx.activity.enableEdgeToEdge
import android.Manifest
import android.app.ProgressDialog
import android.app.ProgressDialog.show
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.calberto_barbosa_jr.firebasehub.databinding.ActivityStorageUploadBinding
import com.calberto_barbosa_jr.firebasehub.helper.DialogManager
import com.calberto_barbosa_jr.firebasehub.helper.PermissionManager
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StorageUploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStorageUploadBinding

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

    private val imageUris = mutableSetOf<Uri>()
    private lateinit var uriImage: Uri
    private lateinit var selectedImageView: ImageView

    private val storage: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            // Adicione a URI ao conjunto apenas se a imagem foi capturada com sucesso
            if (!imageUris.contains(uriImage)) {
                imageUris.add(uriImage)
                displayImage(uriImage)
            }
        } else {
            Toast.makeText(this, "Falha ao tirar foto", Toast.LENGTH_SHORT).show()
        }
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            uriImage = uri
            if (!imageUris.contains(uri)) {
                imageUris.add(uri)
            }
            displayImage(uriImage)
        } else {
            Toast.makeText(this, "Falha ao selecionar imagem", Toast.LENGTH_SHORT).show()
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
        setupPermissions()
        initializeViews()
    }

    private fun initializeViews() {
        binding.showImage1.setOnClickListener { onImageClicked(binding.showImage1) }
        binding.showImage2.setOnClickListener { onImageClicked(binding.showImage2) }
        binding.showImage3.setOnClickListener { onImageClicked(binding.showImage3) }
        binding.uploadButton.setOnClickListener { uploadImage() }
    }

    private fun onImageClicked(imageView: ImageView) {
        selectedImageView = imageView
        showCameraGalleryDialog(
            onCameraSelected = { openCamera() },
            onGallerySelected = { openGallery() }
        )
    }

    private fun showCameraGalleryDialog(
        onCameraSelected: () -> Unit,
        onGallerySelected: () -> Unit
    ) {
        AlertDialog.Builder(this)
            .setTitle("Escolha uma opção")
            .setMessage("Você deseja tirar uma foto ou selecionar da galeria?")
            .setPositiveButton("Câmera") { _, _ -> onCameraSelected() }
            .setNegativeButton("Galeria") { _, _ -> onGallerySelected() }
            .show()
    }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun displayImage(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .error(R.drawable.ic_error) // Ícone de erro para imagens mal carregadas
            .into(selectedImageView)
    }

    private fun uploadImage() {
        if (imageUris.isEmpty()) {
            Toast.makeText(this, "Nenhuma imagem selecionada", Toast.LENGTH_LONG).show()
            return
        }

        val progressDialog = ProgressDialog(this).apply {
            setTitle("Realizando upload...")
            setCancelable(false)
            show()
        }

        val user = Firebase.auth.currentUser
        user?.let {
            val uid = it.uid
            val storageReference = storage.reference.child("image/$uid")

            for (uri in imageUris) {
                val uniqueName = "IMG_${System.currentTimeMillis()}.jpg"
                val fileReference = storageReference.child(uniqueName)

                fileReference.putFile(uri)
                    .addOnSuccessListener { taskSnapshot ->
                        fileReference.downloadUrl.addOnSuccessListener { downloadUri ->
                            showImageUploadDialog(downloadUri.toString())
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Erro ao realizar upload", Toast.LENGTH_LONG).show()
                    }
            }

            progressDialog.dismiss()
            imageUris.clear() // Limpa a lista após o upload
        } ?: run {
            progressDialog.dismiss()
            Toast.makeText(this, "Usuário não autenticado", Toast.LENGTH_LONG).show()
        }
    }

    private fun showImageUploadDialog(imageUrl: String) {
        AlertDialog.Builder(this)
            .setTitle("Upload bem-sucedido")
            .setMessage("URL da imagem: $imageUrl")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun openCamera() {
        val imageFile = createImageFile() ?: run {
            Toast.makeText(this, "Erro ao criar arquivo para a imagem", Toast.LENGTH_LONG).show()
            return
        }

        val authority = "com.calberto_barbosa_jr.firebasehub.fileprovider" // Autoridade do FileProvider
        uriImage = FileProvider.getUriForFile(this, authority, imageFile)

        cameraLauncher.launch(uriImage)
    }

    private fun createImageFile(): File? {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES) ?: return null
        val imageFileName = "IMG_${System.currentTimeMillis()}.jpg"
        return File(storageDir, imageFileName)
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