package com.derich.bigfoot.ui.data

import com.derich.bigfoot.ui.common.LogService
import com.derich.bigfoot.ui.common.LogServiceImpl
import com.derich.bigfoot.ui.common.firestorequeries.StorageService
import com.derich.bigfoot.ui.common.firestorequeries.StorageServiceImpl
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

    @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

    @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService
}