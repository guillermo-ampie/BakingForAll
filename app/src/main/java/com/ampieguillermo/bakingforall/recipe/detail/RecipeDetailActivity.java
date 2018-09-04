package com.ampieguillermo.bakingforall.recipe.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.ampieguillermo.bakingforall.R;
import com.ampieguillermo.bakingforall.model.Recipe;
import com.ampieguillermo.bakingforall.recipe.list.RecipeListActivity;
import org.parceler.Parcels;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity {

  private static final String LOG_TAG = RecipeDetailActivity.class.getSimpleName();
  private static final String EXTRA_RECIPE = "com.ampieguillermo.bakingforall.EXTRA_RECIPE";

  public static Intent getStartIntent(final Context context, final Recipe recipe) {
    final Intent intent = new Intent(context, RecipeDetailActivity.class);

    intent.putExtra(EXTRA_RECIPE, Parcels.wrap(recipe));
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_detail);
    final Toolbar toolbar = findViewById(R.id.toolbar_recipe_detail);
    setSupportActionBar(toolbar);

    // Show the Up button in the action bar.
    final ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // savedInstanceState is non-null when there is fragment state
    // saved from previous configurations of this activity
    // (e.g. when rotating the screen from portrait to landscape).
    // In this case, the fragment will automatically be re-added
    // to its container so we don't need to manually add it.
    // For more information, see the Fragments API guide at:
    //
    // http://developer.android.com/guide/components/fragments.html
    //
    if (savedInstanceState == null) {
      // Create the detail fragment and add it to the activity
      // using a fragment transaction.
      final Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_RECIPE));

      final IngredientListFragment fragment = IngredientListFragment.newInstance(recipe);
      getSupportFragmentManager().beginTransaction()
          .add(R.id.recipe_detail_container, fragment)
          .commit();
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    final int id = item.getItemId();
    if (id == android.R.id.home) {
      // This ID represents the Home or Up button. In the case of this
      // activity, the Up button is shown. For
      // more details, see the Navigation pattern on Android Design:
      //
      // http://developer.android.com/design/patterns/navigation.html#up-vs-back
      //
      navigateUpTo(new Intent(this, RecipeListActivity.class));
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
