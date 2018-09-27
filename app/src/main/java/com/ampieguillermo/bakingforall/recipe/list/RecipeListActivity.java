package com.ampieguillermo.bakingforall.recipe.list;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.ampieguillermo.bakingforall.R;
import com.ampieguillermo.bakingforall.databinding.ActivityRecipeListBinding;
import com.ampieguillermo.bakingforall.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.parceler.Parcels;

/**
 * An activity showing a grid of Recipes. Selecting a recipe launches a
 * RecipeDetailActivity that presents the ingredients, recipe steps and accompanying
 * video in two different layouts, one for handsets and another for tablet-size devices
 */
public class RecipeListActivity extends AppCompatActivity {

  public static final String BUNDLE_RECIPE_LIST = "BUNDLE_RECIPE_LIST";
  private static final String LOG_TAG = RecipeListActivity.class.getSimpleName();
  private ActivityRecipeListBinding binding;
  private List<Recipe> recipeList = Collections.emptyList();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_list);
    setSupportActionBar(binding.toolbarRecipeList);
    if (savedInstanceState == null) { // Load data for the first time
      recipeList = loadJsonData();
    } else { // Recover saved state
      recipeList = Parcels.unwrap(savedInstanceState.getParcelable(BUNDLE_RECIPE_LIST));
    }

    setupRecyclerView();
  }

  @Override
  protected void onSaveInstanceState(final Bundle outState) {
    outState.putParcelable(BUNDLE_RECIPE_LIST, Parcels.wrap(recipeList));
    // Save any view hierarchy
    super.onSaveInstanceState(outState);
  }


  private void setupRecyclerView() {
    final RecyclerView recyclerView = binding.recyclerviewRecipeList;
    final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

    layoutManager.setSpanCount(getResources().getInteger(R.integer.num_columns_layout_recipe_list));
    recyclerView.setHasFixedSize(true);
    final RecipeItemAdapter itemAdapter = new RecipeItemAdapter();

    itemAdapter.setItemList(recipeList);
    recyclerView.setAdapter(itemAdapter);
  }

  // TODO: 8/11/18 Move this to an AsyncTaskLoader
  // TODO: 9/17/18 Read file in a mixed mode: streaming + object in memory
  private List<Recipe> loadJsonData() {
    final Gson gson = new Gson();
    List<Recipe> result = Collections.emptyList();
    try (final InputStream inputStream =
        getAssets().open(getString(R.string.json_data_file_recipe_list))) {

      final Reader reader = new InputStreamReader(inputStream, Charset.defaultCharset());
      result = gson.fromJson(reader, new TypeToken<ArrayList<Recipe>>() {
      }.getType());

    } catch (final FileNotFoundException e) {
      Log.e(LOG_TAG,
          String.format(getString(R.string.error_opening_file), e.getLocalizedMessage()));
    } catch (final IOException e) {
      Log.e(LOG_TAG,
          String.format(getString(R.string.error_io_operation), e.getLocalizedMessage()));
    } catch (final JsonIOException | JsonSyntaxException e) {
      Log.e(LOG_TAG,
          String.format(getString(R.string.error_json_operation), e.getLocalizedMessage()));
    }
    return result;
  }
}

