package com.example.roomtask.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

//Menandakan bahwa interface ini adalah Data Access Object (DAO) untuk Room Database.
@Dao
interface NoteDao {
    // Menyisipkan catatan ke dalam tabel. Jika terjadi konflik dengan kunci utama (id),
    // maka catatan baru akan diabaikan.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    // Memperbarui catatan dalam tabel.
    @Update
    fun update(note: Note)

    // Menghapus catatan dari tabel.
    @Delete
    fun delete(note: Note)

    // Menn mengegambil semua catatan dari tabel dambalikannya sebagai LiveData.
    // LiveData digunakan untuk memantau perubahan data secara otomatis.
    @get:Query("SELECT * from note_table ORDER BY id ASC")
    val allNotes: LiveData<List<Note>>
}