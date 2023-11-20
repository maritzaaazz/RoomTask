package com.example.roomtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roomtask.database.Note
import com.example.roomtask.database.NoteDao
import com.example.roomtask.database.NoteRoomDatabase
import com.example.roomtask.databinding.ActivityHomePageAddBinding
import com.example.roomtask.databinding.ActivityHomePageUpdateBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HomePageUpdate : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageUpdateBinding
    private var id: Int=0
    private lateinit var executorService: ExecutorService
    private lateinit var mNotesDao: NoteDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menggunakan ViewBinding untuk meng-inflate layout
        binding = ActivityHomePageUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan data yang dikirim dari activity sebelumnya menggunakan Intent extras
        val bundle: Bundle? = intent.extras
        id = bundle!!.getInt("EXT_ID")!!
        val title = bundle!!.getString("EXT_TITLE")!!
        val description = bundle!!.getString("EXT_DESCRIPTION")!!
        val date = bundle!!.getString("EXT_DATE")!!

        // Menginisialisasi ExecutorService untuk tugas latar belakang dan mendapatkan NoteDao dari RoomDatabase
        executorService = Executors.newSingleThreadExecutor()
        val db = NoteRoomDatabase.getDatabase(this)
        mNotesDao = db!!.noteDao()!!

        with(binding) {
            // Mengatur nilai EditText dengan data yang diterima
            etTitle.setText(title)
            etDescription.setText(description)
            etDate.setText(date)

            // Mengatur OnClickListener untuk tombol Update
            btnUpdate.setOnClickListener {
                // Memanggil fungsi update dengan objek Note baru dan menyelesaikan aktivitas
                update(
                    Note(
                        id = id,
                        title = etTitle.text.toString(),
                        description = etDescription.text.toString(),
                        date = etDate.text.toString()
                    )
                )
                id = 0
                finish()
            }

            // Mengatur OnClickListener untuk tombol Delete
            btnDelete.setOnClickListener {
                // Memanggil fungsi delete dengan objek Note yang sudah ada dan menyelesaikan aktivitas
                delete(
                    Note(
                        id = id,
                        title = title,
                        description = description,
                        date = date
                    )
                )
                id = 0
                finish()
            }
        }
    }

    // Fungsi untuk memperbarui catatan di latar belakang menggunakan ExecutorService
    private fun update(note: Note) {
        executorService.execute { mNotesDao.update(note) }
    }

    // Fungsi untuk menghapus catatan di latar belakang menggunakan ExecutorService
    private fun delete(note: Note) {
        executorService.execute { mNotesDao.delete(note) }
    }
}