package com.wwe.registration

import com.wwe.user.UserManager
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class RegistrationViewModelTest {
    private lateinit var viewModel: RegistrationViewModel
    private lateinit var userManager: UserManager

    @Before
    fun setup() {
        userManager = Mockito.mock(UserManager::class.java)
        viewModel = RegistrationViewModel(userManager)
    }

    @Test
    fun `Register user calls userManager`() {
        // Given
        viewModel.updateUserData("username", "password")
        viewModel.acceptTCs()

        // When
        viewModel.registerUser()

        // Then
        Mockito.verify(userManager).registerUser("username", "password")
    }
}