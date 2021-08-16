package org.three.minutes.architect.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.three.minutes.architect.domain.usecase.UserUseCase
import org.three.minutes.architect.repository.UserRepository
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideUserUseCase(
        @Named("User") userRepo: UserRepository
    ): UserUseCase =
        UserUseCase(userRepo)
}