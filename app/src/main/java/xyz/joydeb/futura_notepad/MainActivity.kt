package xyz.joydeb.futura_notepad

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val permAudio = registerForActivityResult(
      ActivityResultContracts.RequestPermission()
    ) { }
    permAudio.launch(Manifest.permission.RECORD_AUDIO)

    setContent {
      MaterialTheme { Surface { NoteScreen() } }
    }
  }
}

@Composable
fun NoteScreen() {
  val ctx = LocalContext.current
  var text by remember { mutableStateOf("") }
  Scaffold(
    topBar = { TopAppBar(title = { Text("Futura Notepad") }) },
    floatingActionButton = { ExtendedFloatingActionButton(onClick = { text = "" }, text = { Text("New") }) }
  ) { p ->
    OutlinedTextField(
      value = text,
      onValueChange = { text = it },
      modifier = Modifier.padding(p).fillMaxSize().padding(16.dp),
      label = { Text("Write here") }
    )
  }
}
