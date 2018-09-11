package com.ampieguillermo.bakingforall.recipe.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.ampieguillermo.bakingforall.R;
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
import java.util.Objects;

/**
 * An activity showing a grid of Recipes. Selecting a recipe launches a
 * RecipeDetailActivity that presents the ingredients, recipe steps and accompanying
 * video in two different layouts, one for handsets and another for tablet-size devices
 */
public class RecipeListActivity extends AppCompatActivity {

  private static final String LOG_TAG = RecipeListActivity.class.getSimpleName();

  private SimpleItemAdapter itemAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_list);

    final Toolbar toolbar = findViewById(R.id.toolbar_recipe_list);
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());

    final RecyclerView recyclerView = findViewById(R.id.recyclerview_recipe_list);
    Objects.requireNonNull(recyclerView);
    setupRecyclerView(recyclerView);
    loadJsonData();
  }

  private void setupRecyclerView(@NonNull final RecyclerView recyclerView) {

    final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
    layoutManager.setSpanCount(getResources()
        .getInteger(R.integer.num_columns_layout_recipe_list));

    recyclerView.setHasFixedSize(true);
    itemAdapter = new SimpleItemAdapter();
    itemAdapter.setItemList(loadJsonData());
    recyclerView.setAdapter(itemAdapter);
  }

  // TODO: 8/11/18 Move this to an AsyncTaskLoader
  private List<Recipe> loadJsonData() {
    final Gson gson = new Gson();
    List<Recipe> result = Collections.emptyList();

    try (final InputStream inputStream =
        getAssets().open(getString(R.string.recipe_list_json_data_file))) {

      final Reader reader = new InputStreamReader(inputStream, Charset.defaultCharset());
      result = gson.fromJson(reader, new TypeToken<ArrayList<Recipe>>() {}.getType());

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

