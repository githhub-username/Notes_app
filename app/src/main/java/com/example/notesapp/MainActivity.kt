package com.example.notesapp

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    private lateinit var viewModel: NoteViewModel
    private var recyclerView: RecyclerView = findViewById(R.id.recyclerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this,this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NoteViewModel::class.java]

        viewModel.allNotes.observe(this) { list ->
            list?.let {
                adapter.updateList(it)
            }
        }

    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNode(note)
        Toast.makeText(this,"${note.text} deleted successfully",Toast.LENGTH_LONG).show()
    }

    fun submitData(view: View)
    {
        val input: EditText = findViewById(R.id.input)
        val noteText = input.text.toString()

        if(noteText.isNotEmpty())
        {
            viewModel.insertNote(Note(noteText))
            Toast.makeText(this,"$noteText inserted successfully",Toast.LENGTH_LONG).show()
        }
    }
}