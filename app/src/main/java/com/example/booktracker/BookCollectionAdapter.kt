package com.example.booktracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booktracker.model.BookList


/**
 * Adapter for the [RecyclerView] in [BookCollectionList].
 */

class BookCollectionAdapter(
    private val dataset: List<BookList>,
    private val context: Context,
    private val layoutType: Int
) :
    RecyclerView.Adapter<BookCollectionAdapter.BookCollectionViewHolder>() {

    //Find the item in [book_collection_item_view] by their ID
    class BookCollectionViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_title)
        val imageView: ImageView = view.findViewById(R.id.item_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookCollectionViewHolder {
        //Create a new view
        val adapterLayout: View = when (layoutType){
            0 -> LayoutInflater.from(parent.context).inflate(R.layout.book_collection_item_view, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.book_collection_item_view_grid, parent, false)
        }

        /** DEBUG FOR TYPE
        if (layoutType == 0){
            Log.d("BookCollectionAdapter", "LIST VIEW" )
        } else {
            Log.d("BookCollectionAdapter", "GRID VIEW" )
        }*/


        return BookCollectionViewHolder(adapterLayout)

    }

    override fun onBindViewHolder(holder: BookCollectionViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.stringResourceID
        holder.imageView.setImageBitmap(item.imageResourceID)
    }

    /**
     * Return the size of your dataset (invoked by the layout_manager)
     */
    override fun getItemCount(): Int {
        return dataset.size
    }


}