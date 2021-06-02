package com.example.booktracker.data

import com.example.booktracker.model.BookList

var bookList = mutableListOf<BookList>()

class Datasource {

    fun loadBookList(): MutableList<BookList> {

        return bookList

    }




}



