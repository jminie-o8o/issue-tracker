package com.example.issue_tracker.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindLabelRepository(
        labelRepositoryImpl: LabelRepositoryImpl,
    ): LabelRepository

    @Singleton
    @Binds
    abstract fun bindIssueRepository(
        issueRepositoryImpl: IssueRepositoryImpl,
    ): IssueRepository

    @Singleton
    @Binds
    abstract fun bindMileStoneRepository(
        mileStoneRepositoryImpl: MileStoneRepositoryImpl,
    ): MileStoneRepository

    @Singleton
    @Binds
    abstract fun bindSignUpRepository(
        singUpRepositoryImpl: SingUpRepositoryImpl,
    ): SingUpRepository

    @Singleton
    @Binds
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl,
    ): LoginRepository
}