package com.example.testhiltmvvm.di

import com.example.testhiltmvvm.data.remote.GithubJsonService
import com.example.testhiltmvvm.data.remote.RemoteDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * 为整个APP创建 一个引用的Module
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    /**
     * 为整个APP创建 Retrofit(gson: Gson)
     */
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://my-json-server.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    /**
     * 为整个APP创建 GithubJsonService(retrofit: Retrofit)
     */
    @Provides
    fun provideGithubJsonService(retrofit: Retrofit): GithubJsonService = retrofit.create(
        GithubJsonService::class.java)




    /**
     * 为整个APP创建 RemoteDataSource(githubJsonService: GithubJsonService)
     * 获取数据
     */
    @Singleton
    @Provides
    fun provideRemoteDataSource(githubJsonService: GithubJsonService) = RemoteDataSource(githubJsonService)
}