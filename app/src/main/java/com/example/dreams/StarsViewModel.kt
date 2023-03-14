package com.example.dreams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StarsViewModel: ViewModel() {

    private val stars = MutableLiveData<List<Dream>>()
    val starsList: LiveData<List<Dream>> = stars
    init{
        CoroutineScope(Dispatchers.IO).launch {
            val list = DreamSingl.getDao()?.getAllDreams()
            launch(Dispatchers.Main) {
                stars.value = list
            }
        }
    }
    fun update(){
        CoroutineScope(Dispatchers.IO).launch {
            val list = DreamSingl.getDao()?.getAllDreams()
            launch(Dispatchers.Main) {
                stars.value = list
            }
        }
    }
}