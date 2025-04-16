package dev.brunofelix.fitactions

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import java.util.Locale

class GoogleAssistantReceiver : GlanceAppWidgetReceiver() {

    private lateinit var textToSpeech: TextToSpeech

    override val glanceAppWidget: GlanceAppWidget = GoogleAssistantWidget()

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        execute(context, intent)
    }

    private fun execute(context: Context, intent: Intent) {
        val data = intent.getStringExtra("q")
        val now = System.currentTimeMillis()
        if (now - lastActionTime < 2000) return
        lastActionTime = now

        // TODO: Get intent data
        Log.d("MyTag","Thing: $data")

        // TODO: Validate the User

        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.US
                val textToSpeak = "Your Torus Score is ${getTorusScore()} today"
                Handler(Looper.getMainLooper()).postDelayed({
                    textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "widget_tts")
                }, 2000)
            }
        }
    }

    private fun getTorusScore() = "100"

    companion object {
        private var lastActionTime = 0L
    }
}