package com.calberto_barbosa_jr.firebasehub.storage

import android.content.Context
import android.net.Uri
import com.calberto_barbosa_jr.firebasehub.repository.StorageUploadRepository
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.test.advanceUntilIdle
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doReturn
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.*
import androidx.test.core.app.ApplicationProvider
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.runBlocking
import org.robolectric.RobolectricTestRunner
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [24])
class StorageUploadRepositoryTest {

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        FirebaseApp.initializeApp(context) // Inicializa o Firebase
    }

    @Test
    fun `uploadSingleFile uploads file and returns success`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        FirebaseApp.initializeApp(context)
        val repository = StorageUploadRepository(FirebaseStorage.getInstance())
        val mockUri = Uri.parse("file://mock/image.jpg")
        val pathProvider = object : StoragePathProvider {
            override fun getDirectoryName() = "test_directory"
            override fun getFileName() = "test_image.jpg"
        }

        // Gerenciamento do recurso ParcelFileDescriptor
        val contentResolver = context.contentResolver
        val fileDescriptor = contentResolver.openFileDescriptor(mockUri, "r")
        try {
            val result = repository.uploadSingleFile(mockUri, pathProvider)

            assertTrue(result.isSuccess)
            assertEquals("https://mock.url/image.jpg", result.getOrNull())
        } finally {
            fileDescriptor?.close()
        }
    }


}
