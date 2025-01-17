package com.example.roomdatabase.roomDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Upsert
    suspend fun upsertNotes(note: Note)

    @Delete
    suspend fun deleteNotes(note: Note)

    @Query("SELECT * FROM note")
    fun getAllNotes() : Flow<List<Note>>

}
