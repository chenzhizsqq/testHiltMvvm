package com.example.testhiltmvvm.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val githubJsonService: GithubJsonService
): BaseDataSource() {

    suspend fun getDataList() = getResult { githubJsonService.getDataList() }
}