package com.example.booktracker

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.booktracker.data.Datasource
import com.example.booktracker.databinding.FragmentAddingNewBookPageBinding
import com.example.booktracker.model.BookList
import java.io.FileNotFoundException
import java.io.InputStream


class adding_new_book_page : Fragment() {

    private var _binding: FragmentAddingNewBookPageBinding? = null
    private val binding get() = _binding!!
    private var RESULT_LOAD_IMAGE: Int = 0
    private lateinit var selectedImage : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddingNewBookPageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.submitButton.setOnClickListener {
            Log.d("BUTTON", "Submit Button is being pressed")

            getData()

            val action =
                adding_new_book_pageDirections.actionAddingNewBookPageToBookCollectionList()
            view.findNavController().navigate(action)
        }


        binding.uploadImageButton.setOnClickListener {
            Log.d("BUTTON", "Upload Image Button is being pressed")

            //Intent to open an image
            val imagePickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            imagePickerIntent.type = "image/*"
            startActivityForResult(imagePickerIntent, RESULT_LOAD_IMAGE)


        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                try {
                    val imageURI: Uri = data!!.data!!
                    val imageStream : InputStream? = activity?.contentResolver?.openInputStream(imageURI)
                    selectedImage = BitmapFactory.decodeStream(imageStream)
                    binding.uploadImageButton.setImageBitmap(selectedImage)
                    //Toast.makeText(context, "IMAGE ADDED!", Toast.LENGTH_SHORT).show();


                } catch (e: FileNotFoundException) {
                    e.printStackTrace();
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();

                }

            }
            else -> {
                Toast.makeText(context, "Error on Image Selection", Toast.LENGTH_SHORT).show();
            }


        }


    }

    private fun getData() {
        val newBookTitle = binding.titleInputEditField.text.toString()
        Datasource().loadBookList().add(BookList(newBookTitle, selectedImage))
        Log.d("BUTTON", "getData() is being called")

    }



}

