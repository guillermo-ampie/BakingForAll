package com.ampieguillermo.bakingforall.recipe.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ampieguillermo.bakingforall.databinding.FragmentIngredientListBinding;
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
  private FragmentIngredientListBinding binding;

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
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = FragmentIngredientListBinding.inflate(inflater, container, false);

    if (Objects.requireNonNull(getArguments()).containsKey(Recipe.ARGUMENT_SELECTED_RECIPE)) {
      // Get the Recipe specified by the fragment arguments.
      final Recipe recipe = Parcels
          .unwrap(getArguments().getParcelable(Recipe.ARGUMENT_SELECTED_RECIPE));

      if (recipe != null) {
        setupRecyclerViewIngredientList(recipe);
      }
    }
    return binding.getRoot();
  }

  private void setupRecyclerViewIngredientList(@NonNull final Recipe recipe) {

    binding.recyclerviewIngredientList.setHasFixedSize(true);
    final IngredientItemAdapter itemAdapter = new IngredientItemAdapter();
    itemAdapter.setItemList(recipe.getIngredients());
    binding.recyclerviewIngredientList.setAdapter(itemAdapter);
  }
}
