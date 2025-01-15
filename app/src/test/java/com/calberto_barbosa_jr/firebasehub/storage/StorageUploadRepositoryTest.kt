package com.calberto_barbosa_jr.firebasehub.storage

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

@ExperimentalCoroutinesApi
class StorageUploadRepositoryTest {

    private lateinit var repository: StorageUploadRepository
    private val mockStorage: FirebaseStorage = mock(FirebaseStorage::class.java)
    private val mockReference: StorageReference = mock(StorageReference::class.java)

    @Before
    fun setup() {
        repository = StorageUploadRepository(mockStorage)
        `when`(mockStorage.reference).thenReturn(mockReference)
    }

    @Test
    fun `uploadSingleFile uploads file and returns success`() = runTest(timeout = 30.seconds) {
        val mockUri = Uri.parse("content://mock/image.jpg")
        val mockPathProvider = mock(StoragePathProvider::class.java)
        `when`(mockPathProvider.getDirectoryName()).thenReturn("test_directory")
        `when`(mockPathProvider.getFileName()).thenReturn("test_image.jpg")

        val mockUploadTask = mock(UploadTask::class.java)
        val mockSnapshot = mock(UploadTask.TaskSnapshot::class.java)
        val mockTask = mock(Task::class.java) as Task<Uri>

        `when`(mockReference.child("test_directory/test_image.jpg")).thenReturn(mockReference)
        `when`(mockReference.putFile(mockUri)).thenReturn(mockUploadTask)

        // Simula sucesso no upload
        `when`(mockUploadTask.await()).thenReturn(mockSnapshot)

        // Simula o retorno do download URL
        doReturn(mockTask).`when`(mockReference).downloadUrl
        `when`(mockTask.await()).thenReturn(Uri.parse("https://mock.url/image.jpg"))

        val result = repository.uploadSingleFile(mockUri, mockPathProvider)
        advanceUntilIdle() // Processa tarefas pendentes
        assertTrue(result.isSuccess)
        assertEquals("https://mock.url/image.jpg", result.getOrNull())
    }

    @Test
    fun `uploadSingleFile handles failure`() = runTest {
        val mockUri = Uri.parse("content://mock/image.jpg")
        val mockPathProvider = mock(StoragePathProvider::class.java)
        `when`(mockPathProvider.getDirectoryName()).thenReturn("test_directory")
        `when`(mockPathProvider.getFileName()).thenReturn("test_image.jpg")

        val mockUploadTask = mock(UploadTask::class.java)
        `when`(mockReference.child("test_directory/test_image.jpg")).thenReturn(mockReference)

        // Simula uma falha no upload
        `when`(mockReference.putFile(mockUri)).thenReturn(mockUploadTask)
        `when`(mockUploadTask.await()).thenThrow(RuntimeException("Upload failed"))

        val result = repository.uploadSingleFile(mockUri, mockPathProvider)
        assertTrue(result.isFailure)
        assertEquals("Upload failed", result.exceptionOrNull()?.message)
    }
}
