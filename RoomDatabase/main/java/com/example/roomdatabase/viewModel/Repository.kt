package com.example.roomdatabase.viewModel

import com.example.roomdatabase.roomDb.Note
import com.example.roomdatabase.roomDb.NoteDatabase

class Repository(private val db : NoteDatabase) {
    suspend fun upsertNotes(note: Note) {
        db.dao.upsertNotes(note = note)
    }

    suspend fun deleteNotes(note: Note) {
        db.dao.deleteNotes(note = note)
    }

    fun getAllNotes() = db.dao.getAllNotes()
}