package org.three.minutes.architect.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.three.minutes.architect.repository.UserRepository
import org.three.minutes.architect.repository.UserRepositoryImpl
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    @Named("User")
    abstract fun bindUserRepository(repository: UserRepositoryImpl): UserRepository

}