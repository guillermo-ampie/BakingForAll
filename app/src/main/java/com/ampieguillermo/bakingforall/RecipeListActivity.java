package com.ampieguillermo.bakingforall;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.ampieguillermo.bakingforall.dummy.DummyContent;
import com.ampieguillermo.bakingforall.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity {

  private static final String LOG_TAG = RecipeListActivity.class.getSimpleName();

  /**
   * Whether or not the activity is in two-pane mode, i.e. running on a tablet
   * device.
   */
  /* package */ boolean mTwoPane;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_list);

    final Toolbar toolbar = findViewById(R.id.toolbar_recipe_list);
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());

    final FloatingActionButton fab = findViewById(R.id.fab_recipe_list);
    fab.setOnClickListener(view -> Snackbar
        .make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show());

    if (findViewById(R.id.recipe_detail_container) != null) {
      // The detail container view will be present only in the
      // large-screen layouts (res/values-w900dp).
      // If this view is present, then the
      // activity should be in two-pane mode.
      mTwoPane = true;
    }

    final RecyclerView recyclerView = findViewById(R.id.recyclerview_recipe_list);
    assert recyclerView != null;
    setupRecyclerView(recyclerView);
    loadJsonData();
  }

  private void setupRecyclerView(@NonNull final RecyclerView recyclerView) {

    final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    // Set a divider line
    final DividerItemDecoration dividerLine =
        new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
    recyclerView.addItemDecoration(dividerLine);

    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(new SimpleItemAdapter(this, DummyContent.ITEMS, mTwoPane));
  }

  // TODO: 8/11/18 Move this to an AsyncTaskLoader
  private void loadJsonData() {
    final Gson gson = new Gson();

    try (InputStream inputStream = getAssets().open("baking.json")) {
      final Reader reader = new InputStreamReader(inputStream, Charset.defaultCharset());
      final ArrayList<Recipe> recipeList =
          gson.fromJson(reader, new TypeToken<ArrayList<Recipe>>() {}.getType());
    } catch (final FileNotFoundException e) {
      Log.e(LOG_TAG, String.format("File not found: %s", e.getLocalizedMessage()));
    } catch (final IOException e) {
      Log.e(LOG_TAG, String.format("I/O Error: %s", e.getLocalizedMessage()));
    }
  }
}

