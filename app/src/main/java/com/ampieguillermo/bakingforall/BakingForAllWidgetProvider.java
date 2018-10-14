package com.ampieguillermo.bakingforall;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.ampieguillermo.bakingforall.recipe.list.RecipeListActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakingForAllWidgetProvider extends AppWidgetProvider {

  static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
      int appWidgetId) {

    // Construct the RemoteViews object
    final RemoteViews views = new RemoteViews(context.getPackageName(),
        R.layout.baking_for_all_widget_provider);

    // Create an Intent to launch "RecipeListActivity" when the user clicks on the widget
    final Intent intent = new Intent(context, RecipeListActivity.class);
    // "Wrap" the Intent with a Pending Intent
    final PendingIntent pendingIntent =
        PendingIntent.getActivity(context, 0, intent, 0);

    // Setup views that will launch the Pending Intent
    views.setOnClickPendingIntent(R.id.imageview_appwidget, pendingIntent);
    views.setOnClickPendingIntent(R.id.textview_appwidget, pendingIntent);

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views);
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    // There may be multiple widgets active, so update all of them
    for (int appWidgetId : appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId);
    }
  }

  @Override
  public void onEnabled(Context context) {
    // Enter relevant functionality for when the first widget is created
  }

  @Override
  public void onDisabled(Context context) {
    // Enter relevant functionality for when the last widget is disabled
  }
}

