package com.example.testhiltmvvm.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.testhiltmvvm.data.remote.GithubJsonService
import com.example.testhiltmvvm.data.remote.RemoteDataSource
import com.example.testhiltmvvm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.Retrofit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var mRemoteDataSource: RemoteDataSource

    @Inject
    lateinit var mRetrofit: Retrofit

    /**
     *  在AppModule中的 provideGithubJsonService
     *  自动让系统创建好了
     */
    @Inject
    lateinit var mGithubJsonService: GithubJsonService

    @Inject
    lateinit var mMainViewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.jsonGsonSimple.setOnClickListener { jsonGsonSimple() }

        binding.jsonGsonBaseDataSource.setOnClickListener { jsonGsonBaseDataSource() }

        binding.jsonGsonBaseDataSourceMvvm.setOnClickListener { jsonGsonBaseDataSourceMvvm() }

        binding.jsonGsonBaseDataSourceMvvmFlow.setOnClickListener { jsonGsonBaseDataSourceMvvmFlow() }

        mMainViewModel.postsDataList.observe(this, {
            binding.HelloWorld.text = it.toString()
        })
    }

    private fun jsonGsonBaseDataSourceMvvmFlow() {
        runBlocking {
            flow {
                val getDataList = mRemoteDataSource.getDataList()
                emit(getDataList.data)
            }
                .onStart { Log.e(TAG, "flowViewModel: Starting flow") }
                .onEach {
                    Log.e(TAG, "flowViewModel: onEach : $it")
                    if (it != null) {
                        mMainViewModel.setFlowData(it)
                    }
                }
                .catch { Log.e(TAG, "jsonGsonBaseDataSourceMvvmFlow: error:" + it.message) }
                .onCompletion { if (it == null) Log.e(TAG, "Completed successfully") }
                .collect()
        }
    }

    private fun jsonGsonBaseDataSourceMvvm() {
        CoroutineScope(Dispatchers.IO).launch {
            val getDataList = mRemoteDataSource.getDataList()
            withContext(Dispatchers.Main) {
                getDataList.data?.let { mMainViewModel.setFlowData(it) }
            }
        }
    }

    private fun jsonGsonBaseDataSource() {
        CoroutineScope(Dispatchers.IO).launch {
            val getDataList = mRemoteDataSource.getDataList()
            withContext(Dispatchers.Main) {
                binding.HelloWorld.text = getDataList.toString()
            }
        }

    }

    private fun jsonGsonSimple() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = mGithubJsonService.getResponseGson()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    try {
                        binding.HelloWorld.text = response.body().toString()
                        Log.e(TAG, "jsonGetAllGson: " + response.body())
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