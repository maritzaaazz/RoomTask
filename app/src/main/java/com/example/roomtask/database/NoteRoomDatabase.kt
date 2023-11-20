package com.example.roomtask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


// Menandai kelas sebagai database dengan tabel-tabel tertentu
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteRoomDatabase : RoomDatabase() {

    // Mendeklarasikan metode abstrak untuk mengakses DAO (Data Access Object)
    abstract fun noteDao(): NoteDao?

    // Mendeklarasikan metode dan properti statis untuk mendapatkan instance dari database
    companion object {
        // Volatile digunakan untuk memastikan bahwa INSTANCE selalu terlihat oleh semua thread
        @Volatile
        private var INSTANCE: NoteRoomDatabase? = null

        // Metode untuk mendapatkan instance dari database atau membuatnya jika belum ada
        fun getDatabase(context: Context): NoteRoomDatabase? {
            if (INSTANCE == null) {
                // Menggunakan synchronized untuk memastikan bahwa hanya satu thread yang dapat membuat instance
                synchronized(NoteRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NoteRoomDatabase::class.java, "note_database"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}
