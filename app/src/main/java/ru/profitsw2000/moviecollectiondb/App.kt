package ru.profitsw2000.moviecollectiondb

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.profitsw2000.moviecollectiondb.room.NoteDAO
import ru.profitsw2000.moviecollectiondb.room.NoteDB

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
        appInstance = this
    }

    companion object{
        private var appInstance: App? = null
        private var db: NoteDB? = null
        private const val DB_NAME = "Note.db"

        fun getNoteDao(): NoteDAO {
            if (db == null) {
                synchronized(NoteDB::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(appInstance!!.applicationContext, NoteDB::class.java, DB_NAME)
                            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                            .build()
                    }
                }
            }
            return db!!.noteDao()
        }
    }

}