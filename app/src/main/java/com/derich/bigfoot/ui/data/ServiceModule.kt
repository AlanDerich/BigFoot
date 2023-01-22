package com.derich.bigfoot.ui.data

import com.derich.bigfoot.ui.screens.login.AuthService
import com.derich.bigfoot.ui.screens.login.AuthServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun bindAccountService(
        accountServiceImpl: AuthServiceImpl
    ): AuthService
}