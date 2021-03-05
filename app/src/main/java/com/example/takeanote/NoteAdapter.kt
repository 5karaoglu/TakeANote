package com.example.takeanote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val clickListener: CustomOnClickListener): ListAdapter<Note, NoteAdapter.NoteViewHolder>(NotesComparator()) {

    class NoteViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        private val tvHeader: TextView = itemView.findViewById<TextView>(R.id.tvHeader)
        private val tvDes: TextView = itemView.findViewById<TextView>(R.id.tvDes)
        fun bind(currentNote: Note, clickListener: CustomOnClickListener) {
            tvHeader.text = currentNote.header
            tvDes.text = currentNote.text

            itemView.setOnClickListener {
                clickListener.onItemClicked(currentNote)
            }
        }

        companion object {
            fun create(parent: ViewGroup):NoteViewHolder{
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.single_note,parent,false)
                return NoteViewHolder(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = getItem(position)
        holder.bind(currentNote, clickListener)
    }

    interface CustomOnClickListener{
        fun onItemClicked(currentNote: Note)
    }
}
class NotesComparator(): DiffUtil.ItemCallback<Note>(){
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.text == newItem.text
    }

}
