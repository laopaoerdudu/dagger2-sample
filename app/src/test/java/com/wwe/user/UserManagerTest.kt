package com.wwe.user

import com.wwe.storage.FakeStorage
import com.wwe.storage.Storage
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class UserManagerTest {
    private lateinit var userManager: UserManager
    private lateinit var storage: Storage

    @Test
    fun setup() {
        val userComponentFactory = Mockito.mock(UserComponent.Factory::class.java)
        val userComponent = Mockito.mock(UserComponent::class.java)
        `when`(userComponentFactory.create()).thenReturn(userComponent)

        storage = FakeStorage()
        userManager = UserManager(storage, userComponentFactory)
    }

    @Test
    fun `Username returns what is in the storage`() {
        // When
        userManager.registerUser("username", "password")

        assertEquals("username", userManager.username)
    }

    @Test
    fun `IsUserRegistered behaves as expected`() {
        Assert.assertFalse(userManager.isUserRegistered())

        userManager.registerUser("username", "password")

        Assert.assertTrue(userManager.isUserRegistered())
    }

    @Test
    fun `Register user adds username and password to the storage`() {
        Assert.assertFalse(userManager.isUserRegistered())
        Assert.assertFalse(userManager.isUserLoggedIn())

        userManager.registerUser("username", "password")

        Assert.assertTrue(userManager.isUserRegistered())
        Assert.assertTrue(userManager.isUserLoggedIn())
        assertEquals("username", storage.getString("registered_user"))
        assertEquals("password", storage.getString("usernamepassword"))
    }

    @Test
    fun `Login succeeds when username is registered and password is correct`() {
        userManager.registerUser("username", "password")
        userManager.logout()

        Assert.assertTrue(userManager.loginUser("username", "password"))
        Assert.assertTrue(userManager.isUserLoggedIn())
    }

    @Test
    fun `Login fails when username is not registered`() {
        userManager.registerUser("username", "password")
        userManager.logout()

        Assert.assertFalse(userManager.loginUser("username2", "password"))
        Assert.assertFalse(userManager.isUserLoggedIn())
    }

    @Test
    fun `Login fails when username is registered but password is incorrect`() {
        userManager.registerUser("username", "password")
        userManager.logout()

        Assert.assertFalse(userManager.loginUser("username", "password2"))
        Assert.assertFalse(userManager.isUserLoggedIn())
    }

    @Test
    fun `Unregister behaves as expected`() {
        userManager.registerUser("username", "password")
        Assert.assertTrue(userManager.isUserLoggedIn())

        userManager.unregister()

        Assert.assertFalse(userManager.isUserLoggedIn())
        Assert.assertFalse(userManager.isUserRegistered())
    }
}