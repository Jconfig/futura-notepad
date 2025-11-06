package xyz.joydeb.futura_notepad.feature

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput

data class Stroke(val points: List<Offset>)

@Composable
fun DrawingPad(modifier: Modifier = Modifier, onChange: (List<Stroke>) -> Unit) {
  var strokes by remember { mutableStateOf<List<Stroke>>(emptyList()) }
  var current by remember { mutableStateOf<List<Offset>>(emptyList()) }

  Canvas(modifier.pointerInput(Unit) {
    detectDragGestures(
      onDragStart = { current = listOf(it) },
      onDrag = { _, delta -> current = current + (current.last() + delta) },
      onDragEnd = {
        if (current.isNotEmpty()) {
          strokes = strokes + Stroke(current)
          current = emptyList()
          onChange(strokes)
        }
      }
    )
  }) {
    fun drawStroke(s: Stroke) {
      if (s.points.size < 2) return
      val path = Path().apply {
        moveTo(s.points.first().x, s.points.first().y)
        s.points.drop(1).forEach { lineTo(it.x, it.y) }
      }
      drawPath(path, Color.Black)
    }
    strokes.forEach { drawStroke(it) }
    if (current.size > 1) drawStroke(Stroke(current))
  }
}
