package com.wwe.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wwe.LiveDataTestUtil
import com.wwe.user.UserManager
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class LoginViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel
    private lateinit var userManager: UserManager

    @Before
    fun setup() {
        userManager = mock(UserManager::class.java)
        viewModel = LoginViewModel(userManager)
    }

    @Test
    fun `getUsername`() {
        // Given
        Mockito.`when`(userManager.username).thenReturn("David")

        // When
        val userName = viewModel.getUsername()

        // Then
        assertEquals("David", userName)
    }

    @Test
    fun `unregister`() {
        // When
        viewModel.unregister()

        // Then
        verify(userManager).unregister()
    }

    @Test
    fun `Login emits error`() {
        // Given
        Mockito.`when`(
            userManager.loginUser(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
            )
        ).thenReturn(false)

        // When
        viewModel.login("David", "123")

        // Then
        assertEquals(LiveDataTestUtil.getValue(viewModel.loginState), LoginError)
    }
}