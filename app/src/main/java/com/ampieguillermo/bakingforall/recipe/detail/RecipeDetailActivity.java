package com.ampieguillermo.bakingforall.recipe.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import com.ampieguillermo.bakingforall.R;
import com.ampieguillermo.bakingforall.databinding.ActivityRecipeDetailBinding;
import com.ampieguillermo.bakingforall.model.Recipe;
import com.ampieguillermo.bakingforall.recipe.detail.recipestepcontent.RecipeStepContentActivity;
import com.ampieguillermo.bakingforall.recipe.list.RecipeListActivity;
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
  public static final String ARGUMENT_RECIPE = "ARGUMENT_RECIPE";
  private ActivityRecipeDetailBinding binding;
  /**
   * Whether or not the activity is in two-pane mode, i.e. running on a tablet
   * device.
   */
  /* package */ boolean mTwoPane;

  public static Intent getStartIntent(final Context context, final Recipe recipe) {
    final Intent intent = new Intent(context, RecipeDetailActivity.class);

    /** When using a Samsung "Galaxy 7 Prime"(SM-G610M) with Android 7.0 the following error and
     * warning appeared in Logcat:
     * "E/Parcel: Class not found when unmarshalling:
     * com.ampieguillermo.bakingforall.model.Recipe$$Parcelable
     * java.lang.ClassNotFoundException: com.ampieguillermo.bakingforall.model.Recipe$$Parcelable"
     *
     * "W/Bundle: Failed to parse Bundle, but defusing quietly
     * android.os.BadParcelableException: ClassNotFoundException when unmarshalling:
     * com.ampieguillermo.bakingforall.model.Recipe$$Parcelable"
     *
     * Note-1: In spite of this error, the App does not crash
     * Note-2: For Parcelable objects, the App uses the Parceler Library
     * Note-3: The error does not appears in the emulators
     * Workaround: Instead of putting the "Recipe" object in the Intent's EXTRA, the Recipe is
     * firstly put in a Bundle object, and later on, this bundle is sent as the Intent's EXTRA
     * Reference: https://stackoverflow.com/questions/28589509/android-e-parcel-class-not
     * -found-when-unmarshalling-only-on-samsung-tab3
     */
    final Bundle bundle = new Bundle();
    bundle.putParcelable(ARGUMENT_RECIPE, Parcels.wrap(recipe));
//    intent.putExtra(EXTRA_RECIPE, Parcels.wrap(recipe));
    intent.putExtra(EXTRA_RECIPE, bundle);

    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);
    setSupportActionBar(binding.toolbarRecipeDetail);

    // Show the Up button in the action bar.
    final ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    if (binding.idLayoutRecipeDetail.recipeStepContentContainer != null) {
      // The detail container view will be present only in the
      // large-screen layouts (res/values-w900dp).
      // If this view is present, then the
      // activity should be in two-pane mode.
      mTwoPane = true;
    }

    final Intent intent = getIntent();
    if (intent.hasExtra(EXTRA_RECIPE)) {
      // Get the Recipe specified by the fragment arguments.
      final Bundle bundle = intent.getBundleExtra(EXTRA_RECIPE);
      final Recipe recipe = Parcels.unwrap(bundle.getParcelable(ARGUMENT_RECIPE));
//      final Recipe recipe = Parcels.unwrap(intent.getParcelableExtra(EXTRA_RECIPE));

      if (recipe != null) {
        final CollapsingToolbarLayout appBarLayout = binding.ctoolbarlayoutRecipeDetail;
        if (appBarLayout != null) {
          appBarLayout.setTitle(recipe.getName());
        }
        setupRecyclerView(recipe);
      }
    }
  }

  private void setupRecyclerView(final Recipe recipe) {
    final RecyclerView recyclerView = binding.idLayoutRecipeDetail.recyclerviewRecipeDetailList;

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
