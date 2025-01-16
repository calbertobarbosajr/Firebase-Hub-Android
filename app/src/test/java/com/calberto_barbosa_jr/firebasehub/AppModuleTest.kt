package com.calberto_barbosa_jr.firebasehub

import com.calberto_barbosa_jr.firebasehub.repository.FirestoreRepository
import com.calberto_barbosa_jr.firebasehub.repository.FirestoreRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AppModuleTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // Injeta as dependências fornecidas pelo Hilt
    @Inject
    lateinit var firestore: FirebaseFirestore

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var repository: FirestoreRepository

    @Before
    fun setup() {
        hiltRule.inject() // Inicializa as dependências injetadas
    }

    @Test
    fun testFirebaseFirestoreInjection() {
        // Verifica se o FirebaseFirestore foi injetado corretamente
        assertNotNull(firestore)
    }

    @Test
    fun testFirebaseAuthInjection() {
        // Verifica se o FirebaseAuth foi injetado corretamente
        assertNotNull(auth)
    }

    @Test
    fun testFirestoreRepositoryInjection() {
        // Verifica se o FirestoreRepository foi injetado corretamente
        assertNotNull(repository)
        assertTrue(repository is FirestoreRepositoryImpl)
    }
}
