package com.beomsu317.data

import com.beomsu317.data.chat.roomDataModule
import com.beomsu317.data.config.databaseModule
import com.beomsu317.data.user.userDataModule

val dataModules = listOf(
    databaseModule,
    userDataModule,
    roomDataModule
)