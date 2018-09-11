package com.ampieguillermo.bakingforall.recipe.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.ampieguillermo.bakingforall.R;
import com.ampieguillermo.bakingforall.model.Recipe;
import com.ampieguillermo.bakingforall.recipe.detail.recipestepcontent.RecipeStepContentActivity;
import com.ampieguillermo.bakingforall.recipe.list.RecipeListActivity;
import java.util.Objects;
import org.parceler.Parcels;

/**
 * An activity representing a single Recipe detail screen.  This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items (ingredients and recipe steps),
 * which when touched, lead to a {@link RecipeStepContentActivity} representing
 * item details (detailed step instructions & video). On tablets, the activity
 * presents the list of items and item details side-by-side using two vertical panes.
 */
public class RecipeDetailActivity extends AppCompatActivity {

  private static final String LOG_TAG = RecipeDetailActivity.class.getSimpleName();
  private static final String EXTRA_RECIPE =
      "com.ampieguillermo.bakingforall.recipe.detail.EXTRA_RECIPE";
  /**
   * Whether or not the activity is in two-pane mode, i.e. running on a tablet
   * device.
   */
  /* package */ boolean mTwoPane;

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

    if (findViewById(R.id.recipe_step_content_container) != null) {
      // The detail container view will be present only in the
      // large-screen layouts (res/values-w900dp).
      // If this view is present, then the
      // activity should be in two-pane mode.
      mTwoPane = true;
    }

    final Intent intent = getIntent();
    if (intent.hasExtra(EXTRA_RECIPE)) {
      // Get the Recipe specified by the fragment arguments.
      final Recipe recipe = Parcels.unwrap(intent.getParcelableExtra(EXTRA_RECIPE));

      if (recipe != null) {
        final CollapsingToolbarLayout appBarLayout =
            findViewById(R.id.ctoolbarlayout_recipe_detail);
        if (appBarLayout != null) {
          appBarLayout.setTitle(recipe.getName());
        }

        final RecyclerView recyclerView = findViewById(R.id.recyclerview_recipe_detail_list);
        Objects.requireNonNull(recyclerView);
        setupRecyclerView(recyclerView, recipe);
      }
    }
  }

  private void setupRecyclerView(@NonNull final RecyclerView recyclerView, final Recipe recipe) {

    recyclerView.setHasFixedSize(true);
    final RecipeStepItemAdapter itemAdapter =
        new RecipeStepItemAdapter(this, mTwoPane);
    itemAdapter.setItemList(recipe.getSteps());
    recyclerView.setAdapter(itemAdapter);
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
