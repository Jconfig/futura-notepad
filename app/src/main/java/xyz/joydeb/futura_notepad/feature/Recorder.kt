package xyz.joydeb.futura_notepad.feature

import android.media.MediaRecorder
import java.io.File

class Recorder(private val outFile: File) {
  private var recorder: MediaRecorder? = null
  fun start() {
    stop()
    recorder = MediaRecorder().apply {
      setAudioSource(MediaRecorder.AudioSource.MIC)
      setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
      setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
      setAudioEncodingBitRate(128_000)
      setAudioSamplingRate(44_100)
      setOutputFile(outFile.absolutePath)
      prepare()
      start()
    }
  }
  fun stop() {
    try { recorder?.stop() } catch (_: Exception) {}
    try { recorder?.release() } catch (_: Exception) {}
    recorder = null
  }
}
