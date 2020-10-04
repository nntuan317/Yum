package com.nntuan317.yum.di

import com.nntuan317.yum.data.user.UserRepository
import com.nntuan317.yum.data.user.UserRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class UserModule {
    @Binds
    abstract fun bindUserRepository(userRepositoryImp: UserRepositoryImp): UserRepository
}