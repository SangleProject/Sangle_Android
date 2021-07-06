package org.three.minutes.util

import androidx.lifecycle.MutableLiveData

class ListLiveData<T> : MutableLiveData<MutableList<T>>() {

    init {
        value = mutableListOf()
    }

    fun add(item : T){
        val items = value
        items?.add(item)
        value = items
    }

    fun addAll(list : MutableList<T>){
        val items = value
        items?.addAll(list)
        value = items
    }

    fun clear(){
        val items = value
        items?.clear()

        value = items
    }

    fun remove(item : T){
        val items = value
        items?.remove(item)
        value = items
    }

    fun removeAt(index : Int){
        val items = value
        items?.removeAt(index)
        value = items
    }
}