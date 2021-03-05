package com.example.takeanote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment : Fragment(),
NoteAdapter.CustomOnClickListener{
    private val viewModel: MainViewModel by viewModels {
        CustomViewModelFactory((requireActivity().application as NoteApplication).repository)
    }
    private val SPAN_COUNT = 2
    //space between recyclerview items as px
    private val ITEM_SPACE = 20

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = requireActivity().findViewById<RecyclerView>(R.id.recyclerMain)
        val adapter = NoteAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(),SPAN_COUNT,LinearLayoutManager.VERTICAL,false)
        recyclerView.addItemDecoration(CustomItemDecoration(ITEM_SPACE))

        //observer
        viewModel.notes.observe(viewLifecycleOwner,{ notes ->
            notes?.let {
                adapter.submitList(notes)
            }
        })
        ///fab onclick
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addFragment)
        }
    }

    override fun onItemClicked(currentNote: Note) {
       val bundle = Bundle()
        bundle.putInt("id",currentNote.nId)
        bundle.putString("header",currentNote.header)
        bundle.putString("text",currentNote.text)
        findNavController().navigate(R.id.action_mainFragment_to_addFragment,bundle)
    }


}