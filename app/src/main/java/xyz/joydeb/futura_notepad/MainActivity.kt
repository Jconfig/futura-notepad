package xyz.joydeb.futura_notepad

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val permAudio = registerForActivityResult(
      ActivityResultContracts.RequestPermission()
    ) { }
    LaunchedEffect(Unit) { permAudio.launch(Manifest.permission.RECORD_AUDIO) }

    setContent {
      MaterialTheme { Surface { NoteScreen() } }
    }
  }
}

@Composable
fun NoteScreen() {
  var text by remember { mutableStateOf("") }
  Scaffold(
    topBar = { TopAppBar(title = { Text("Futura Notepad") }) },
    floatingActionButton = {
      FloatingActionButton(onClick = { text = "" }) { Text("New") }
    }
  ) { padding ->
    OutlinedTextField(
      value = text,
      onValueChange = { text = it },
      modifier = Modifier
        .padding(padding)
        .fillMaxSize()
        .padding(16.dp),
      label = { Text("Write here") }
    )
  }
}
