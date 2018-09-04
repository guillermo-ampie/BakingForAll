package com.ampieguillermo.bakingforall.recipe.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ampieguillermo.bakingforall.R;
import com.ampieguillermo.bakingforall.model.Recipe;
import com.ampieguillermo.bakingforall.recipe.list.RecipeListActivity;
import java.util.Objects;
import org.parceler.Parcels;

/**
 * A fragment representing a single Recipe list of ingredients screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class IngredientListFragment extends Fragment {

  private static final String LOG_TAG = IngredientListFragment.class.getSimpleName();

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public IngredientListFragment() {
    // Mandatory empty constructor
  }


  public static IngredientListFragment newInstance(final Recipe recipe) {
    final IngredientListFragment fragment = new IngredientListFragment();
    final Bundle args = new Bundle();

    args.putParcelable(Recipe.ARGUMENT_SELECTED_RECIPE, Parcels.wrap(recipe));
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View rootView =
        inflater.inflate(R.layout.fragment_ingredient_list, container, false);

    if (Objects.requireNonNull(getArguments()).containsKey(Recipe.ARGUMENT_SELECTED_RECIPE)) {
      // Get the Recipe specified by the fragment arguments.
      final Recipe recipe = Parcels
          .unwrap(getArguments().getParcelable(Recipe.ARGUMENT_SELECTED_RECIPE));

      if (recipe != null) {
        final Activity activity = getActivity();
        final CollapsingToolbarLayout appBarLayout = Objects.requireNonNull(activity)
            .findViewById(R.id.ctoolbarlayout_recipe_detail);
        if (appBarLayout != null) {
          appBarLayout.setTitle(recipe.getName());
        }
        final RecyclerView recyclerViewIngredientList =
            rootView.findViewById(R.id.recyclerview_ingredient_list);
        assert (recyclerViewIngredientList != null);
        setupRecyclerViewIngredientList(recipe, recyclerViewIngredientList);
      }
    }
    return rootView;
  }

  private void setupRecyclerViewIngredientList(@NonNull final Recipe recipe,
      @NonNull final RecyclerView recyclerViewIngredientList) {
    final LinearLayoutManager layoutManager =
        (LinearLayoutManager) recyclerViewIngredientList.getLayoutManager();

    recyclerViewIngredientList.setHasFixedSize(true);

    final IngredientItemAdapter itemAdapter = new IngredientItemAdapter();

    itemAdapter.setItemList(recipe.getIngredients());
    recyclerViewIngredientList.setAdapter(itemAdapter);
  }
}
