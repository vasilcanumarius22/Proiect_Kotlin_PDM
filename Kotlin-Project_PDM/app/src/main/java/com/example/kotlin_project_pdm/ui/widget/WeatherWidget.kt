package com.example.kotlin_project_pdm.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import androidx.preference.PreferenceManager
import com.example.kotlin_project_pdm.R

/**
 * Implementation of App Widget functionality.
 */
class WeatherWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    var views = RemoteViews(context.packageName, R.layout.weather_widget)

    // Instruct the widget manager to update the widget




    val sp = context.getSharedPreferences("weatherSP", Context.MODE_PRIVATE)
    val city = sp.getString("city", "ERROR_CITY").toString()
    val temp = sp.getFloat("temp", 0f)


    var temperaturaText = ""
    val tipTemperatura = PreferenceManager.getDefaultSharedPreferences(context)
        .getString("tip_temperatura", null).toString()

    if(tipTemperatura == "Celsius")
    {
        temperaturaText = "Temperature: ${temp}\u00B0 Celsius"
    }
    else {
        val tempFahrenheit = (temp * 1.8 + 32)
        temperaturaText= "Temperature: ${String.format(
            "%.2f", tempFahrenheit)}\u00B0 Fahrenheit"
    }


    views.setTextViewText(R.id.tvCityWidget, city)
    views.setTextViewText(R.id.tvTempWidget, temperaturaText)




    appWidgetManager.updateAppWidget(appWidgetId, views)
}