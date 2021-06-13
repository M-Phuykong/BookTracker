package com.example.booktracker

import androidx.lifecycle.ViewModel

class BookCollectionViewModel: ViewModel() {

    private var _isLinear : Boolean = true
    val isLinear: Boolean
        get() = _isLinear

    fun changeLayout(layoutType : Int){
        _isLinear = layoutType != 0
    }














}