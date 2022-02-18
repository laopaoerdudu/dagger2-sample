package com.wwe.user

import javax.inject.Inject
import kotlin.random.Random

@LoggedUserScope
class UserDataRepository @Inject constructor(private val userManager: UserManager) {

    val username: String
        get() = userManager.username

    var unreadNotifications: Int

    init {
        unreadNotifications = randomInt()
    }

    fun refreshUnreadNotifications() {
        unreadNotifications = randomInt()
    }
}

fun randomInt(): Int {
    return Random.nextInt(until = 100)
}