package com.ampieguillermo.bakingforall.widget;

import android.app.IntentService;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import com.ampieguillermo.bakingforall.model.Recipe;
import org.parceler.Parcels;

public class ShowIngredientsService extends IntentService {

  //
  // Service Actions
  //
  public static final String ACTION_SHOW_INGREDIENTS =
      "com.ampieguillermo.bakingforall.widget.action.ACTION_SHOW_INGREDIENTS";

  //
  // Keys for Intent EXTRAS
  //
  private static final String EXTRA_RECIPE = "com.ampieguillermo.bakingforall.widget.extra.EXTRA_RECIPE";

  private static final String LOG_TAG = ShowIngredientsService.class.getSimpleName();

  /**
   * Creates an IntentService.  Invoked by your subclass's constructor.
   */
  public ShowIngredientsService() {
    super(LOG_TAG);
  }

  public static void startActionShowIngredients(final Context context, final Recipe recipe) {
    final Intent intent = new Intent(context, ShowIngredientsService.class);
    intent.setAction(ACTION_SHOW_INGREDIENTS);

    intent.putExtra(EXTRA_RECIPE, Parcels.wrap(recipe));
    context.startService(intent);
  }

  /**
   * This method is invoked on the worker thread with a request to process.
   * Only one Intent is processed at a time, but the processing happens on a
   * worker thread that runs independently from other application logic.
   * So, if this code takes a long time, it will hold up other requests to
   * the same IntentService, but it will not hold up anything else.
   * When all requests have been handled, the IntentService stops itself,
   * so you should not call {@link #stopSelf}.
   *
   * @param intent The value passed to {@link
   * Context#startService(Intent)}.
   * This may be null if the service is being restarted after
   * its process has gone away; see
   * {@link Service#onStartCommand}
   * for details.
   */
  @Override
  protected void onHandleIntent(@Nullable final Intent intent) {
    if (intent != null) {
      final String action = intent.getAction();
      if (ACTION_SHOW_INGREDIENTS.equals(action) && intent.hasExtra(EXTRA_RECIPE)) {
        final Recipe recipe = Parcels.unwrap(intent.getParcelableExtra(EXTRA_RECIPE));
        handleActionShowIngredients(recipe);
      }
    }
  }

  private void handleActionShowIngredients(final Recipe recipe) {
    Log.v(LOG_TAG, ">>>>> Handling action ShowIngredients");
    final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
    // Get all widgets & update them
    final int[] appWidgetIds =
        appWidgetManager.getAppWidgetIds(new ComponentName(this,
            BakingForAllWidgetProvider.class));
    BakingForAllWidgetProvider.updateAppWidgets(this, appWidgetManager, appWidgetIds, recipe);
  }
}
