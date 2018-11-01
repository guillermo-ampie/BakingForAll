package com.ampieguillermo.bakingforall.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import com.ampieguillermo.bakingforall.R;
import com.ampieguillermo.bakingforall.model.Recipe;
import com.ampieguillermo.bakingforall.recipe.detail.RecipeDetailActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakingForAllWidgetProvider extends AppWidgetProvider {

  static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
      final int appWidgetId, final Recipe recipe) {

    Log.v("updateAppWidget", ">>>>> updating widget: " + recipe);
    // Construct the RemoteViews object
    final RemoteViews views = new RemoteViews(context.getPackageName(),
        R.layout.baking_for_all_widget_provider);

    // Create an Intent to start Activity "RecipeDetailActivity" when the user clicks on the widget
    if (recipe != null) {
      final Intent intent = RecipeDetailActivity.getStartIntent(context, recipe);
      // "Wrap" the Intent with a Pending Intent
      final PendingIntent pendingIntent =
          PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

      // Setup views that will launch the Pending Intent when clicked
      views.setOnClickPendingIntent(R.id.imageview_appwidget, pendingIntent);
      views.setOnClickPendingIntent(R.id.textview_appwidget, pendingIntent);

      views.setTextViewText(R.id.textview_appwidget, recipe.getName());
    } else {
      views.setTextViewText(R.id.textview_appwidget, context.getString(R.string.appwidget_text));
      views.setOnClickPendingIntent(R.id.imageview_appwidget, null);
      views.setOnClickPendingIntent(R.id.textview_appwidget, null);
      Log.v("updateAppWidget", ">>>>> recipe is NULL");
    }

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views);
  }

  public static void updateAppWidgets(final Context context,
      final AppWidgetManager appWidgetManager,
      final int[] appWidgetIds,
      final Recipe recipe) {

    // There may be multiple widgets active, so update all of them
    for (int appWidgetId : appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
      Log.v("updateAppWidget", ">>>>> appWidgetId: " + appWidgetId);
    }

  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    // There may be multiple widgets active, so update all of them
    for (int appWidgetId : appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId, null);
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

