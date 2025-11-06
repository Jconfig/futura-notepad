package xyz.joydeb.futura_notepad.data

import android.content.Context
import androidx.room.*

@Database(
  entities = [Note::class, NoteFts::class, Attachment::class],
  version = 1,
  exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDb : RoomDatabase() {
  abstract fun notes(): NoteDao
  abstract fun attachments(): AttachmentDao

  companion object {
    @Volatile private var INSTANCE: AppDb? = null
    fun get(context: Context): AppDb =
      INSTANCE ?: synchronized(this) {
        INSTANCE ?: Room.databaseBuilder(
          context.applicationContext,
          AppDb::class.java, "app.db"
        ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
      }
  }
}
