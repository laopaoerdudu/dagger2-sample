package com.wwe.main

import com.wwe.user.UserDataRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class MainViewModelTest {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var userDataRepository: UserDataRepository

    @Before
    fun setup() {
        userDataRepository = Mockito.mock(UserDataRepository::class.java)
        mainViewModel = MainViewModel(userDataRepository)
    }

    @Test
    fun `Welcome text returns right text`() {
        Mockito.`when`(userDataRepository.username).thenReturn("username")

        Assert.assertEquals("Hello username!", mainViewModel.welcomeText)
    }

    @Test
    fun `Notifications text returns right text`() {
        Mockito.`when`(userDataRepository.unreadNotifications).thenReturn(5)

        Assert.assertEquals("You have 5 unread notifications", mainViewModel.notificationsText)
    }
}