package com.example.roomtask.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Mendeklarasikan kelas data "Note" sebagai entitas yang akan disimpan dalam database Room
@Entity(tableName = "note_table")
data class Note(
    // Menentukan kolom utama dan menyertakan opsi untuk menghasilkan nilai id secara otomatis
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,

    // Menentukan nama kolom "title" dalam tabel database
    @ColumnInfo(name = "title")
    val title: String,

    // Menentukan nama kolom "description" dalam tabel database
    @ColumnInfo(name = "description")
    val description: String,

    // Menentukan nama kolom "date" dalam tabel database
    @ColumnInfo(name = "date")
    val date: String
)