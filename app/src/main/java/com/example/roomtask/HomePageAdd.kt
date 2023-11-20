package com.example.roomtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roomtask.database.Note
import com.example.roomtask.database.NoteDao
import com.example.roomtask.database.NoteRoomDatabase
import com.example.roomtask.databinding.ActivityHomePageAddBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HomePageAdd : AppCompatActivity() {
    private lateinit var mNotesDao: NoteDao
    private lateinit var executorService: ExecutorService
    private lateinit var binding: ActivityHomePageAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menggunakan ViewBinding untuk mengakses tampilan layout activity_home_page_add.xml
        binding = ActivityHomePageAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Membuat ExecutorService dengan satu thread untuk melakukan operasi database secara asinkron
        executorService = Executors.newSingleThreadExecutor()

        // Mendapatkan instance dari database menggunakan NoteRoomDatabase
        val db = NoteRoomDatabase.getDatabase(this)

        // Menginisialisasi NoteDao dari database
        mNotesDao = db!!.noteDao()!!

        // Menambahkan OnClickListener pada tombol btnCreate
        binding.btnCreate.setOnClickListener {
            // Memanggil fungsi insert dengan membuat objek Note baru dari input pengguna
            insert(
                Note(
                    title = binding.etTitle.text.toString(),
                    description = binding.etDescription.text.toString(),
                    date = binding.etDate.text.toString(),
                )
            )

            // Menutup activity setelah berhasil menyimpan catatan
            finish()
        }
    }

    // Fungsi untuk menyisipkan catatan ke dalam database menggunakan ExecutorService
    private fun insert(note: Note) {
        executorService.execute { mNotesDao.insert(note) }
    }
}