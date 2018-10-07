package com.ampieguillermo.bakingforall.recipe.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.view.MenuItem;
import com.ampieguillermo.bakingforall.R;
import com.ampieguillermo.bakingforall.databinding.ActivityRecipeDetailBinding;
import com.ampieguillermo.bakingforall.model.Recipe;
import com.ampieguillermo.bakingforall.recipe.detail.recipestepcontent.RecipeStepContentActivity;
import com.ampieguillermo.bakingforall.recipe.list.RecipeListActivity;
import com.ampieguillermo.bakingforall.utils.RecipeAssets;
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

  public static final int INGREDIENT_LIST_TAB_INDEX = 0;
  public static final int RECIPE_STEP_LIST_INDEX = 1;
  private static final String LOG_TAG = RecipeDetailActivity.class.getSimpleName();
  //
  // Keys for Intent EXTRAS
  //
  private static final String EXTRA_RECIPE =
      "com.ampieguillermo.bakingforall.recipe.detail.EXTRA_RECIPE";

  //
  // Keys for Bundles
  //
  private static final String BUNDLE_RECIPE = "BUNDLE_RECIPE";

  /**
   * Whether or not the activity is in two-pane mode, i.e. running on a tablet
   * device.
   */
  /* package */ boolean mTwoPane;
  private ActivityRecipeDetailBinding binding;
  private TabLayout.OnTabSelectedListener tabSelectedListener;

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
     * Note-4: Happens in Intent.getParcelableExtra(String name)
     * Workaround: Instead of putting the "Recipe" object in the Intent's EXTRA, the Recipe is
     * firstly put in a Bundle object, and later on, this bundle is sent as the Intent's EXTRA
     * Reference: https://stackoverflow.com/questions/28589509/android-e-parcel-class-not
     * -found-when-unmarshalling-only-on-samsung-tab3
     */
    final Bundle bundle = new Bundle();
    bundle.putParcelable(BUNDLE_RECIPE, Parcels.wrap(recipe));
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
      // large-screen layouts (res/values-sw600dp).
      // If this view is present, then the
      // activity should be in two-pane mode.
      mTwoPane = true;
    }

    final Intent intent = getIntent();
    if (intent.hasExtra(EXTRA_RECIPE)) {
      // Get the Recipe specified by the fragment arguments.
      final Bundle bundle = intent.getBundleExtra(EXTRA_RECIPE);
      final Recipe recipe =
          Parcels.unwrap(Objects.requireNonNull(bundle.getParcelable(BUNDLE_RECIPE)));
//      final Recipe recipe = Parcels.unwrap(intent.getParcelableExtra(EXTRA_RECIPE));

      if (recipe != null) {
        setTitle(recipe.getName());
        binding.imageviewRecipeDetail
            .setImageResource(RecipeAssets.getPhotoAsset(recipe.getName()));

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Setup Fragments
        final IngredientListFragment ingredientListFragment =
            IngredientListFragment.newInstance(recipe);
        final RecipeStepListFragment recipeStepListFragment =
            RecipeStepListFragment.newInstance(recipe, mTwoPane);

        viewPagerAdapter.addFragmentPage(ingredientListFragment,
            getString(R.string.recipe_detail_ingredients_tab_label));
        viewPagerAdapter.addFragmentPage(recipeStepListFragment,
            getString(R.string.recipe_detail_recipe_steps_tab_label));

        binding.idLayoutRecipeDetail.viewpagerLayoutRecipeDetail.setAdapter(viewPagerAdapter);
        binding.tablayoutRecipeDetail
            .setupWithViewPager(binding.idLayoutRecipeDetail.viewpagerLayoutRecipeDetail);

        setTabLayoutIcons();
//        binding.idLayoutRecipeDetail.viewpagerLayoutRecipeDetail.setCurrentItem(0);
      }
    }
  }

  @Override
  protected void onStart() {
    super.onStart();

    addOnTabSelectedListener();
  }

  @Override
  protected void onStop() {
    super.onStop();

    removeOnTabSelectedListener();
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

  private void setTabLayoutIcons() {

    // TODO: 10/6/18 Use a ColorStateList to get system's colors, instead than my own. Here and in
    // addOnTabSelectedListener
    //ColorStateList colorList = binding.tablayoutRecipeDetail.getTabTextColors();
    // getTabIconTint()
    final int colorTabIcon = getResources().getColor(R.color.colorTabIcon);
    final int colorTabIconSelected = getResources().getColor(R.color.colorTabIconSelected);

    final Drawable ingredientListDrawable =
        AppCompatResources.getDrawable(this,
            R.drawable.ic_ingredient_list_24dp);
    ingredientListDrawable.setColorFilter(colorTabIconSelected, PorterDuff.Mode.SRC_ATOP);
    binding.tablayoutRecipeDetail
        .getTabAt(INGREDIENT_LIST_TAB_INDEX).setIcon(ingredientListDrawable);

    final Drawable recipeStepsDrawable =
        AppCompatResources.getDrawable(this, R.drawable.ic_steps_white_24dp);
    recipeStepsDrawable.setColorFilter(colorTabIcon, PorterDuff.Mode.SRC_ATOP);
    binding.tablayoutRecipeDetail
        .getTabAt(RECIPE_STEP_LIST_INDEX).setIcon(recipeStepsDrawable);
  }

  private void addOnTabSelectedListener() {
    // Manage icon colors according to tab state

    tabSelectedListener =
        new TabLayout.ViewPagerOnTabSelectedListener(binding
            .idLayoutRecipeDetail
            .viewpagerLayoutRecipeDetail) {

          private void setIconColor(final Tab tab, int color) {
            final Drawable tabIcon = tab.getIcon();
            tabIcon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
          }

          @Override
          public void onTabUnselected(final Tab tab) {
            super.onTabUnselected(tab);
            setIconColor(tab, getResources().getColor(R.color.colorTabIcon));
          }

          @Override
          public void onTabSelected(final Tab tab) {
            super.onTabSelected(tab);
            setIconColor(tab, getResources().getColor(R.color.colorTabIconSelected));
          }

        };

    binding.tablayoutRecipeDetail.addOnTabSelectedListener(tabSelectedListener);
  }

  private void removeOnTabSelectedListener() {
    binding.tablayoutRecipeDetail.removeOnTabSelectedListener(tabSelectedListener);
  }
}
