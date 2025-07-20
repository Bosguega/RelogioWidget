package com.example.relogiowidget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.*
import java.text.DateFormat
import java.util.Locale
import java.util.Date

class ClockWidget : AppWidgetProvider() {

    companion object {
        private const val ACTION_CLOCK_UPDATE = "com.example.relogiowidget.ACTION_CLOCK_UPDATE"
        private const val INTERVAL_MINUTE = 60 * 1000L
    }

    override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) {
        ids.forEach { updateAppWidget(context, manager, it) }
        scheduleNextUpdate(context)
    }

    override fun onEnabled(context: Context) {
        scheduleNextUpdate(context)
    }

    override fun onDisabled(context: Context) {
        cancelAlarm(context)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        val shouldUpdate = intent.action == ACTION_CLOCK_UPDATE
        if (!shouldUpdate) return

        val manager = AppWidgetManager.getInstance(context)
        val ids = manager.getAppWidgetIds(ComponentName(context, ClockWidget::class.java))

        ids.forEach { updateAppWidget(context, manager, it) }

        if (intent.action == ACTION_CLOCK_UPDATE) {
            scheduleNextUpdate(context)
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val locale = Locale.getDefault()
        val now = Date()

        val timeFormat = SimpleDateFormat("HH:mm", locale)
        val currentTime = timeFormat.format(now)

        val dayOfWeek = SimpleDateFormat("EEEE", locale).format(now)
        val longDate = DateFormat.getDateInstance(DateFormat.LONG, locale).format(now)

        val regex = if (locale.language == "pt") {
            Regex(",?\\s*de?\\s*\\d{4}$")
        } else {
            Regex(",?\\s*\\d{4}$")
        }

        var dateWithoutYear = longDate.replace(regex, "").trim()

        if (locale.language == "pt" && dateWithoutYear.endsWith(" de")) {
            dateWithoutYear = dateWithoutYear.dropLast(3).trim()
        }

        var currentDate = "$dayOfWeek, $dateWithoutYear".replaceFirstChar { it.uppercaseChar() }

        // Capitalizar a primeira letra do mês (última palavra)
        val words = currentDate.split(' ').toMutableList()
        if (words.size >= 2) {
            val lastWord = words.last()
            words[words.lastIndex] = lastWord.replaceFirstChar { it.uppercaseChar() }
        }
        currentDate = words.joinToString(" ")

        val views = RemoteViews(context.packageName, R.layout.widget_clock)
        views.setTextViewText(R.id.widget_clock_text, currentTime)
        views.setTextViewText(R.id.widget_date_text, currentDate)

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
    private fun scheduleNextUpdate(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = getPendingIntent(context)

        val now = System.currentTimeMillis()
        val nextMinute = (now / INTERVAL_MINUTE + 1) * INTERVAL_MINUTE

        alarmManager.setRepeating(
            AlarmManager.RTC,
            nextMinute,
            INTERVAL_MINUTE,
            pendingIntent
        )
    }

    private fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(getPendingIntent(context))
    }

    private fun getPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, ClockWidget::class.java).apply {
            action = ACTION_CLOCK_UPDATE
        }

        return PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}