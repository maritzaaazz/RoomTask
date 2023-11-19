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
        binding = ActivityHomePageUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        id = bundle!!.getInt("EXT_ID")!!
        val title = bundle!!.getString("EXT_TITLE")!!
        val description = bundle!!.getString("EXT_DESCRIPTION")!!
        val date = bundle!!.getString("EXT_DATE")!!

        executorService = Executors.newSingleThreadExecutor()
        val db = NoteRoomDatabase.getDatabase(this)
        mNotesDao = db!!.noteDao()!!

        with(binding){
            etTitle.setText(title)
            etDescription.setText(description)
            etDate.setText(date)

            btnUpdate.setOnClickListener{
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

            btnDelete.setOnClickListener{
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

    private fun update(note: Note){
        executorService.execute{mNotesDao.update(note)}
    }

    private fun delete(note: Note){
        executorService.execute{mNotesDao.delete(note)}
    }
}