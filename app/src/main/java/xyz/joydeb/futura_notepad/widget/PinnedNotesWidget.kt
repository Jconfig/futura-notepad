package xyz.joydeb.futura_notepad.widget

import androidx.compose.runtime.Composable
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Column
import androidx.glance.text.Text

class PinnedNotesWidget : GlanceAppWidget() {
  override suspend fun provideGlance(context: android.content.Context, id: GlanceId) {
    provideContent { GlanceTheme { WidgetUi() } }
  }
  @Composable private fun WidgetUi() {
    Column(GlanceModifier) { Text("Pinned Notes") }
  }
}
class PinnedNotesWidgetReceiver : GlanceAppWidgetReceiver() {
  override val glanceAppWidget: GlanceAppWidget = PinnedNotesWidget()
}
