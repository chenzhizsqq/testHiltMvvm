package com.example.testhiltmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.testhiltmvvm.data.remote.GithubJsonService
import com.example.testhiltmvvm.data.remote.RemoteDataSource
import com.example.testhiltmvvm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var mRemoteDataSource : RemoteDataSource

    @Inject
    lateinit var mRetrofit : Retrofit

    /**
     *  在AppModule中的 provideGithubJsonService
     *  自动让系统创建好了
     */
    @Inject
    lateinit var mGithubJsonService : GithubJsonService

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jsonGetAllGsonInject()
    }


    private fun jsonGetAllGsonInject() {
        CoroutineScope(Dispatchers.IO ).launch {
            val response = mGithubJsonService.getResponseGson()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    try {
                        binding.HelloWorld.text = response.body().toString()
                        Log.e(TAG, "jsonGetAllGson: "+response.body() )
                    } catch (e: Exception) {
                        Log.e(TAG, "jsonGetPosts: ", e)
                    }
                } else {
                    Log.e(TAG, "jsonGetPosts: error:" + response.message())
                }
            }
        }
    }
    companion object {
        private const val TAG = "MainActivity"
    }
}