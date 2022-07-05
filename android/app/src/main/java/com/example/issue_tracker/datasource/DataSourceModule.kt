package com.example.issue_tracker.datasource

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun dataSourceBind(
        dataSourceImpl: DataSourceImpl,
    ): DataSource

    @Singleton
    @Binds
    abstract fun oauthSourceBind(
        oAuthDataSource: OAuthDataSourceImpl,
    ): OAuthDataSource
}