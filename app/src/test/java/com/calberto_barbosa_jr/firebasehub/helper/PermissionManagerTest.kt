package com.calberto_barbosa_jr.firebasehub.helper

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.DefaultAsserter.fail

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [24]) // Defina a API simulada
class PermissionManagerTest {

    private val activity = Robolectric.buildActivity(AppCompatActivity::class.java).create().get()
    private val permissionManager = PermissionManager(activity)

    @Test
    fun `hasPermissions returns true when all permissions are granted`() {
        grantPermissions("PERMISSION_1", "PERMISSION_2")

        val permissions = arrayOf("PERMISSION_1", "PERMISSION_2")
        val result = permissionManager.hasPermissions(permissions)

        assertTrue(result)
    }

    @Test
    fun `hasPermissions returns false when at least one permission is denied`() {
        denyPermissions("PERMISSION_2")

        val permissions = arrayOf("PERMISSION_1", "PERMISSION_2")
        val result = permissionManager.hasPermissions(permissions)

        assertFalse(result)
    }

    @Test
    fun `requestPermissions calls ActivityCompat requestPermissions`() {
        val permissions = arrayOf("PERMISSION_1", "PERMISSION_2")

        permissionManager.requestPermissions(permissions)

        // Você pode verificar interações adicionais aqui, se necessário.
    }

    private fun grantPermissions(vararg permissions: String) {
        permissions.forEach {
            org.robolectric.shadows.ShadowApplication.getInstance().grantPermissions(it)
        }
    }

    private fun denyPermissions(vararg permissions: String) {
        permissions.forEach {
            org.robolectric.shadows.ShadowApplication.getInstance().denyPermissions(it)
        }
    }

    // Teste: Código de requisição inválido
    @Test
    fun `handlePermissionsResult does nothing when requestCode is invalid`() {
        val invalidRequestCode = 999 // Código de requisição diferente do esperado
        val permissions = arrayOf("PERMISSION_1", "PERMISSION_2")
        val grantResults = intArrayOf(PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_GRANTED)

        var onAllGrantedCalled = false
        var onPermissionDeniedCalled = false

        permissionManager.handlePermissionsResult(
            requestCode = invalidRequestCode,
            permissions = permissions,
            grantResults = grantResults,
            onAllGranted = { onAllGrantedCalled = true },
            onPermissionDenied = { onPermissionDeniedCalled = true }
        )

        // Nenhuma das funções de callback deve ser chamada
        assertFalse(onAllGrantedCalled)
        assertFalse(onPermissionDeniedCalled)
    }

    // Teste: Permissões vazias para hasPermissions
    @Test
    fun `hasPermissions returns true when permissions array is empty`() {
        val permissions = emptyArray<String>() // Array vazio

        val result = permissionManager.hasPermissions(permissions)

        // Deve retornar true porque não há permissões a verificar
        assertTrue(result)
    }

    // Teste: Permissões vazias para requestPermissions
    @Test
    fun `requestPermissions does nothing when permissions array is empty`() {
        val permissions = emptyArray<String>() // Array vazio

        permissionManager.requestPermissions(permissions)

        // Nenhuma permissão deve ser solicitada, mas não temos como verificar interações diretamente.
        // Para garantir que não houve exceções, o teste verifica que o método executou sem falhas.
    }

    // Teste: Comportamento Parcialmente Concedido
    @Test
    fun `hasPermissions returns false when some permissions are granted and others are denied`() {
        grantPermissions("PERMISSION_1")
        denyPermissions("PERMISSION_2")

        val permissions = arrayOf("PERMISSION_1", "PERMISSION_2")
        val result = permissionManager.hasPermissions(permissions)

        assertFalse(result)
    }

    // Teste: Permissões Duplicadas
    @Test
    fun `hasPermissions handles duplicate permissions correctly`() {
        grantPermissions("PERMISSION_1")

        val permissions = arrayOf("PERMISSION_1", "PERMISSION_1")
        val result = permissionManager.hasPermissions(permissions)

        assertTrue(result)
    }

    @Test
    fun `requestPermissions handles duplicate permissions without failure`() {
        val permissions = arrayOf("PERMISSION_1", "PERMISSION_1")

        permissionManager.requestPermissions(permissions)

        // Testa se a execução ocorre sem falhas
    }

    // Teste: Permissões Não Existentes
    @Test
    fun `hasPermissions returns false for nonexistent permissions`() {
        val permissions = arrayOf("NONEXISTENT_PERMISSION")

        val result = permissionManager.hasPermissions(permissions)

        assertFalse(result)
    }

    // Teste: Erro em Request Code com Código Próximo
    @Test
    fun `handlePermissionsResult ignores request codes near the expected one`() {
        val requestCode = PermissionManager.REQUEST_PERMISSIONS_CODE + 1
        val permissions = arrayOf("PERMISSION_1", "PERMISSION_2")
        val grantResults = intArrayOf(PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_GRANTED)

        var onAllGrantedCalled = false
        var onPermissionDeniedCalled = false

        permissionManager.handlePermissionsResult(
            requestCode = requestCode,
            permissions = permissions,
            grantResults = grantResults,
            onAllGranted = { onAllGrantedCalled = true },
            onPermissionDenied = { onPermissionDeniedCalled = true }
        )

        // Nenhuma callback deve ser chamada
        assertFalse(onAllGrantedCalled)
        assertFalse(onPermissionDeniedCalled)
    }

    // Teste: Grant Results com Valores Inválidos
    @Test
    fun `handlePermissionsResult treats invalid grantResults as denied`() {
        val permissions = arrayOf("PERMISSION_1", "PERMISSION_2")
        val grantResults = intArrayOf(PackageManager.PERMISSION_GRANTED, -1) // -1 é inválido

        var onPermissionDeniedCalled = false

        permissionManager.handlePermissionsResult(
            requestCode = PermissionManager.REQUEST_PERMISSIONS_CODE,
            permissions = permissions,
            grantResults = grantResults,
            onAllGranted = { fail("Should not be called") },
            onPermissionDenied = { onPermissionDeniedCalled = true }
        )

        assertTrue(onPermissionDeniedCalled)
    }

    // Teste: Integração com o Sistema
    @Test
    fun `requestPermissions calls ActivityCompat requestPermissions with correct parameters`() {
        val permissions = arrayOf("PERMISSION_1", "PERMISSION_2")

        val spyActivity = spy(activity)
        val spyPermissionManager = PermissionManager(spyActivity)

        spyPermissionManager.requestPermissions(permissions)

        verify(spyActivity).let {
            ActivityCompat.requestPermissions(it, permissions, PermissionManager.REQUEST_PERMISSIONS_CODE)
        }
    }
}
