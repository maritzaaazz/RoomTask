package com.example.roomtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.roomtask.database.Note
import com.example.roomtask.database.NoteDao
import com.example.roomtask.database.NoteRoomDatabase
import com.example.roomtask.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var mNotesDao: NoteDao
    private lateinit var executorService: ExecutorService
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi executor service untuk menjalankan operasi database di thread terpisah
        executorService = Executors.newSingleThreadExecutor()

        // Mendapatkan instance dari database
        val db = NoteRoomDatabase.getDatabase(this)
        mNotesDao = db!!.noteDao()!!

        // Set listener untuk tombol tambah, untuk membuka halaman tambah note
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, HomePageAdd::class.java)
            startActivity(intent)
        }

        // Set listener untuk item di ListView, untuk membuka halaman update note
        binding.listView.setOnItemClickListener { adapterView, view, i, id ->
            val item = adapterView.adapter.getItem(i) as Note
            val intent = Intent(this@MainActivity, HomePageUpdate::class.java)
            // Mengirim data note ke halaman update
            intent.putExtra("EXT_ID", item.id)
            intent.putExtra("EXT_TITLE", item.title)
            intent.putExtra("EXT_DESCRIPTION", item.description)
            intent.putExtra("EXT_DATE", item.date)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Memanggil fungsi untuk mendapatkan dan menampilkan semua catatan dari database
        getAllNotes()
    }

    // Fungsi untuk mendapatkan semua catatan dan menampilkan dalam ListView
    private fun getAllNotes() {
        mNotesDao.allNotes.observe(this) { notes ->
            // Membuat adapter untuk ListView dengan data catatan yang diperoleh dari database
            val adapter: ArrayAdapter<Note> = ArrayAdapter<Note>(
                this,
                android.R.layout.simple_list_item_1, notes
            )
            // Menetapkan adapter ke ListView
            binding.listView.adapter = adapter
        }
    }
}