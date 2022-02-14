package com.example.testhiltmvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testhiltmvvm.data.entities.PostsData
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    //专门对应json数据中的List<PostsData>
    private val _postsDataList = MutableLiveData<List<PostsData>>()

    val postsDataList: LiveData<List<PostsData>> = _postsDataList


    fun setFlowData(listTestFlowData: List<PostsData>) {
        _postsDataList.value = listTestFlowData
    }
}
