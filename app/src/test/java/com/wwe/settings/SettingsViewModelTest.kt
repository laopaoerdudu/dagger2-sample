package com.wwe.settings

import com.wwe.user.UserDataRepository
import com.wwe.user.UserManager
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class SettingsViewModelTest {
    private lateinit var viewModel: SettingsViewModel
    private lateinit var userDataRepository: UserDataRepository
    private lateinit var userManager: UserManager

    @Before
    fun setup() {
        userDataRepository = Mockito.mock(UserDataRepository::class.java)
        userManager = Mockito.mock(UserManager::class.java)
        viewModel = SettingsViewModel(userDataRepository, userManager)
    }

    @Test
    fun `Refresh notifications works as expected`() {
        viewModel.refreshNotifications()

        Mockito.verify(userDataRepository).refreshUnreadNotifications()
    }

    @Test
    fun `Logout works as expected`() {
        viewModel.logout()

        Mockito.verify(userManager).logout()
    }
}