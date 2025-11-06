package xyz.joydeb.futura_notepad.data

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface NoteDao {
  @Query("SELECT * FROM notes WHERE deletedAt IS NULL ORDER BY pinned DESC, updatedAt DESC")
  fun pageNotes(): PagingSource<Int, Note>

  @Query("SELECT * FROM notes WHERE id=:id")
  suspend fun get(id: String): Note?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsert(note: Note)

  @Query("UPDATE notes SET deletedAt = strftime('%s','now')*1000 WHERE id=:id")
  suspend fun softDelete(id: String)

  @Query("SELECT notes.* FROM notes JOIN notes_fts ON notes.rowid = notes_fts.rowid WHERE notes_fts MATCH :q AND deletedAt IS NULL ORDER BY updatedAt DESC")
  fun search(q: String): PagingSource<Int, Note>
}

@Dao
interface AttachmentDao {
  @Query("SELECT * FROM attachments WHERE noteId = :noteId ORDER BY createdAt DESC")
  suspend fun listForNote(noteId: String): List<Attachment>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(a: Attachment)

  @Query("DELETE FROM attachments WHERE id=:id")
  suspend fun delete(id: String)
}
