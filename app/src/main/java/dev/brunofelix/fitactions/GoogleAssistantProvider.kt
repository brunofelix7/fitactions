package dev.brunofelix.fitactions

import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.core.graphics.drawable.IconCompat
import androidx.slice.Slice
import androidx.slice.SliceProvider
import androidx.slice.builders.ListBuilder
import androidx.slice.builders.SliceAction
import androidx.slice.builders.list
import androidx.slice.builders.row
import dev.brunofelix.fitactions.tasks.TasksActivity

class GoogleAssistantProvider : SliceProvider() {

    override fun onCreateSliceProvider(): Boolean {
        return true
    }

    override fun onMapIntentToUri(intent: Intent): Uri {
        return Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(context?.packageName)
            .appendPath("google_assistant_slice")
            .build()
    }

    override fun onBindSlice(sliceUri: Uri): Slice? {
        val context = context ?: return null

        return when (sliceUri.path) {
            "/google_assistant_slice" -> {
                val icon = IconCompat.createWithResource(context, R.drawable.ic_add)
                val intent = Intent(context, TasksActivity::class.java)
                intent.action = Intent.ACTION_VIEW
                intent.putExtra("google_assistant_slice", "open_torus_score")
                val pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
                val sliceAction = SliceAction.create(
                    pendingIntent,
                    icon,
                    ListBuilder.ICON_IMAGE,
                    "Open Torus app"
                )
                list(context, sliceUri, ListBuilder.INFINITY) {
                    row {
                        setTitle("Your Torus Score today is ${getTorusScore()}")
                        setPrimaryAction(sliceAction)
                    }
                }
            }
            else -> null
        }
    }

    private fun getTorusScore(): String {
        return "65"
    }
}