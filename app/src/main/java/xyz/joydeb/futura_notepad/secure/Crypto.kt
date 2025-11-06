package xyz.joydeb.futura_notepad.secure

import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import java.io.File
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec
import android.util.Base64
import kotlin.random.Random

class Crypto(private val context: Context) {
  private val masterKey = MasterKey.Builder(context)
    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
    .build()

  private val keyFile = File(context.filesDir, "k.bin")

  private fun getKey(): ByteArray {
    if (!keyFile.exists()) {
      val kgen = KeyGenerator.getInstance("AES")
      kgen.init(256)
      val raw = kgen.generateKey().encoded
      val enc = EncryptedFile.Builder(
        context, keyFile, masterKey,
        EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
      ).build()
      enc.openFileOutput().use { it.write(raw) }
      return raw
    }
    val enc = EncryptedFile.Builder(
      context, keyFile, masterKey,
      EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
    ).build()
    return enc.openFileInput().use { it.readBytes() }
  }

  fun encrypt(plain: ByteArray): ByteArray {
    val iv = Random.nextBytes(12)
    val key = javax.crypto.spec.SecretKeySpec(getKey(), "AES")
    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, key, GCMParameterSpec(128, iv))
    val ct = cipher.doFinal(plain)
    return iv + ct
  }

  fun decrypt(ciphertext: ByteArray): ByteArray {
    val iv = ciphertext.copyOfRange(0, 12)
    val ct = ciphertext.copyOfRange(12, ciphertext.size)
    val key = javax.crypto.spec.SecretKeySpec(getKey(), "AES")
    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(128, iv))
    return cipher.doFinal(ct)
  }

  fun encryptString(s: String) = Base64.encodeToString(encrypt(s.toByteArray()), Base64.NO_WRAP)
  fun decryptString(b64: String) = String(decrypt(Base64.decode(b64, Base64.NO_WRAP)))
}
