package com.example.kotlin_project_pdm.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import androidx.preference.PreferenceManager
import com.example.kotlin_project_pdm.R

// WeatherWidget class that provides functionality for the weather widget
class WeatherWidget : AppWidgetProvider() {

    // Update the widget at intervals defined by the system or by the app
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Iterates through all instances of the widget and updates them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    // onEnabled and onDisabled are optional, but good to have functions (just in case)
    // Called when the first instance of the widget is added to the home screen
    override fun onEnabled(context: Context) {
        // relevant functionality for when the first widget is created
    }

    // Called when the last instance of the widget is removed from the home screen
    override fun onDisabled(context: Context) {
        // relevant functionality for when the last widget is disabled
    }
}

// Updates the app widget with current weather data
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Constructs the RemoteViews object which will be used to update the widget's layout
    var views = RemoteViews(context.packageName, R.layout.weather_widget)

    // Retrieve the stored weather data
    val sp = context.getSharedPreferences("weatherSP", Context.MODE_PRIVATE)
    val city = sp.getString("city", "ERROR_CITY").toString()
    val temp = sp.getFloat("temp", 0f)

    // Get the temperature format preference (Celsius or Fahrenheit)
    var temperaturaText = ""
    val tipTemperatura = PreferenceManager.getDefaultSharedPreferences(context)
        .getString("tip_temperatura", null).toString()

    // Prepare the temperature string based on the user's preference
    if (tipTemperatura == "Celsius") {
        temperaturaText = "Temperature: ${temp}\u00B0 Celsius"
    } else {
        val tempFahrenheit = (temp * 1.8 + 32)
        temperaturaText = "Temperature: ${
            String.format(
                "%.2f", tempFahrenheit
            )
        }\u00B0 Fahrenheit"
    }

    // Update widget views with weather data
    views.setTextViewText(R.id.tvCityWidget, city)
    views.setTextViewText(R.id.tvTempWidget, temperaturaText)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}