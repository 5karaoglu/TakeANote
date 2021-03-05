package com.example.takeanote

import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController


class AddFragment : Fragment() {

    private val viewModel : MainViewModel by viewModels {
        CustomViewModelFactory((requireActivity().application as NoteApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etHeader = requireActivity().findViewById<EditText>(R.id.etHeader)
        val etDes = requireActivity().findViewById<EditText>(R.id.etDes)
        val buttonSave = requireActivity().findViewById<Button>(R.id.buttonSave)
        val buttonCancel = requireActivity().findViewById<Button>(R.id.buttonCancel)
        val buttonDelete = requireActivity().findViewById<Button>(R.id.buttonDelete)

        arguments?.let {
            buttonDelete.visibility = View.VISIBLE
        }

        buttonSave.setOnClickListener {
            if (!etHeader.text.isNullOrEmpty()){
                //if bundle not null this means update
                if (arguments != null){
                    val note = Note(requireArguments().getInt("id"),etHeader.text.toString(),etDes.text.toString())
                    viewModel.update(note)
                }else{
                    val note = Note(0,etHeader.text.toString(),etDes.text.toString())
                    viewModel.insert(note)
                }
                findNavController().navigate(R.id.action_addFragment_to_mainFragment)
            }else{
                Toast.makeText(requireContext(), "You have to enter a header !", Toast.LENGTH_SHORT).show()
            }
        }
        buttonCancel.setOnClickListener {
            findNavController().popBackStack()
        }
        buttonDelete.setOnClickListener {
             arguments?.let {
                 AlertDialog.Builder(requireContext())
                         .setCancelable(false)
                         .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
                         .setPositiveButton("DELETE") { _, _ ->
                             viewModel.delete(Note(it.getInt("id"), it.getString("header")!!,it.getString("text")!!))
                             findNavController().navigate(R.id.action_addFragment_to_mainFragment)
                         }
                         .setMessage("Are you sure you want to delete this note ?")
                         .show()
             }
        }

        arguments?.let {
            val tvToolbarText = requireActivity().findViewById<TextView>(R.id.tvToolbarText)
            tvToolbarText.text = "Edit Text"
            etHeader.setText(it.getString("header"))
            etDes.setText(it.getString("text"))
        }

    }


}