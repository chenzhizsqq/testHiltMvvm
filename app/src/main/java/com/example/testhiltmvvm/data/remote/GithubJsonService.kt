package com.example.testhiltmvvm.data.remote

import com.example.testhiltmvvm.data.entities.PostsData
import com.example.testhiltmvvm.data.entities.PostsDataList
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET


interface GithubJsonService {

    //https://raw.githubusercontent.com/chenzhizsqq/testJson/main/db.json中，
    /*"test":
    {
        "id": 333,
        "title": "fdsafdsaf333"
    }*/

    //放在网络上，https://github.com/chenzhizsqq/testJson/blob/main/db.json
    //app用的是，https://raw.githubusercontent.com/chenzhizsqq/testJson/main/db.json
    @GET("/chenzhizsqq/testJson/test")
    suspend fun getResponse(): Response<ResponseBody>

    //放在网络上，https://github.com/chenzhizsqq/testJson/blob/main/db.json
    //app用的是，https://raw.githubusercontent.com/chenzhizsqq/testJson/main/db.json
    @GET("/chenzhizsqq/testJson/posts")
    suspend fun getResponseGson(): Response<List<PostsData>>


    @GET("/chenzhizsqq/testJson/posts")
    suspend fun getDataList(): Response<PostsDataList>

    @GET("/chenzhizsqq/testJson/posts")
    suspend fun getResponseMoshi(): Response<List<PostsData>>


}