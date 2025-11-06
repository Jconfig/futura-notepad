package xyz.joydeb.futura_notepad.feature

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.tasks.await

object Ocr {
  suspend fun recognize(bitmap: Bitmap): String {
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.Builder().build())
    val res = recognizer.process(InputImage.fromBitmap(bitmap, 0)).await()
    return res.text
  }
}
