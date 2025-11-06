package xyz.joydeb.futura_notepad.data

import androidx.room.*
import java.util.*

@Entity(tableName = "notes")
data class Note(
  @PrimaryKey val id: String = UUID.randomUUID().toString(),
  val title: String = "",
  val body: String = "",
  val format: String = "md",
  val tags: List<String> = emptyList(),
  val pinned: Boolean = false,
  val createdAt: Long = System.currentTimeMillis(),
  val updatedAt: Long = System.currentTimeMillis(),
  val deletedAt: Long? = null
)

@Fts4(contentEntity = Note::class)
@Entity(tableName = "notes_fts")
data class NoteFts(
  @PrimaryKey @ColumnInfo(name = "rowid") val rowId: Int = 0,
  val title: String,
  val body: String
)

@Entity(
  tableName = "attachments",
  foreignKeys = [ForeignKey(
    entity = Note::class,
    parentColumns = ["id"],
    childColumns = ["noteId"],
    onDelete = ForeignKey.CASCADE
  )],
  indices = [Index("noteId")]
)
data class Attachment(
  @PrimaryKey val id: String = UUID.randomUUID().toString(),
  val noteId: String,
  val kind: String,
  val uri: String,
  val createdAt: Long = System.currentTimeMillis()
)

class Converters {
  @TypeConverter fun listToString(list: List<String>?): String = list?.joinToString("|") ?: ""
  @TypeConverter fun stringToList(s: String?): List<String> = if (s.isNullOrEmpty()) emptyList() else s.split("|")
}
