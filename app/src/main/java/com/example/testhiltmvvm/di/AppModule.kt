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
 * @Provides就是专门为整个系统封装不用创建的对象
 * 只要用@Inject来声名，就可以自动为你封装好
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * 为整个APP创建 Gson 的 对象
     */
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    /**
     * 为整个APP创建 Retrofit 的 对象
     */
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://my-json-server.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    /**
     * 为整个APP创建 GithubJsonService 的 对象
     */
    @Provides
    fun provideGithubJsonService(retrofit: Retrofit): GithubJsonService = retrofit.create(
        GithubJsonService::class.java)




    /**
     * 为整个APP创建 RemoteDataSource
     * 获取数据
     */
    @Singleton
    @Provides
    fun provideRemoteDataSource(githubJsonService: GithubJsonService) = RemoteDataSource(githubJsonService)
}