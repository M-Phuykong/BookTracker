package com.example.booktracker

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booktracker.data.Datasource
import com.example.booktracker.databinding.FragmentBookCollectionListBinding


class BookCollectionList : Fragment() {


    private var _binding: FragmentBookCollectionListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: BookCollectionViewModel by viewModels()

    private var layoutType: Int = 0

    companion object{
        const val B = "BUTTON"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookCollectionListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView

        setLayout()

        binding.floatingAddButton.setOnClickListener{
            Log.d(B,"ADD BUTTON IS PRESSED")
            //Move to [fragment_adding_new_book_page]
            val action = BookCollectionListDirections.actionBookCollectionListToAddingNewBookPage()
            view.findNavController().navigate(action)
        }

        binding.bottomAppBar.setNavigationOnClickListener{
            ///MENU BUTTON (THREE DASH) -> DO SOMETHING
            Log.d(B,"NAVIGATION BUTTON IS PRESSED")
        }

        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    Log.d(B,"SEARCH BUTTON IS PRESSED")
                    // Handle search icon press
                    true
                }
                R.id.more -> {
                    // Handle more item (inside overflow menu) press
                    Log.d(B,"MORE INSIDE BUTTON IS PRESSED")
                    true
                }
                else -> false
            }
        }

        val textView: TextView = view.findViewById(R.id.text_view_list_item)
        textView.text = Datasource().loadBookList().size.toString()


    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
        val layoutButton = menu.findItem(R.id.action_switch_layout)
        setIcon(layoutButton)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                if (viewModel.isLinear){
                    viewModel.changeLayout(0)
                } else viewModel.changeLayout(1)
                setLayout()
                setIcon(item)
                return true
            }
            else -> super.onOptionsItemSelected(item)

        }

    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null)
            return
        menuItem.icon =
            if (viewModel.isLinear)
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_grid_view)
            else ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_list_view)
    }

    private fun setLayout() {
        if (viewModel.isLinear) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            layoutType = 0

        } else {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            layoutType = 1

        }
        recyclerView.adapter = BookCollectionAdapter(Datasource().loadBookList(), requireContext(), layoutType)

    }


}