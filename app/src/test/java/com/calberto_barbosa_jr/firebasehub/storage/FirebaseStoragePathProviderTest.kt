package com.calberto_barbosa_jr.firebasehub.storage

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertNotEquals
import org.junit.Test

class FirebaseStoragePathProviderTest {

    @Test
    fun `getDirectoryName returns correct directory`() {
        val provider = FirebaseStoragePathProvider("test_directory")
        assertEquals("test_directory", provider.getDirectoryName())
    }

    @Test
    fun `getFileName returns unique file name`() {
        val provider = FirebaseStoragePathProvider("test_directory")
        val fileName1 = provider.getFileName()
        val fileName2 = provider.getFileName()
        assertNotEquals(fileName1, fileName2)
        assertTrue(fileName1.startsWith("IMG_"))
        assertTrue(fileName1.endsWith(".jpg"))
    }
}
