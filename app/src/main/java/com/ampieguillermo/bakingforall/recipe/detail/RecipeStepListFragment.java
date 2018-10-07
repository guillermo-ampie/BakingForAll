package com.ampieguillermo.bakingforall.recipe.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ampieguillermo.bakingforall.databinding.FragmentRecipeStepListBinding;
import com.ampieguillermo.bakingforall.model.Recipe;
import com.ampieguillermo.bakingforall.recipe.list.RecipeListActivity;
import java.util.Objects;
import org.parceler.Parcels;

/**
 * A fragment representing a single Recipe list of steps screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeStepListFragment extends Fragment {

  private static final String LOG_TAG = RecipeStepListFragment.class.getSimpleName();

  //
  // Keys for Fragment arguments
  //
  // Flag to indicate if the UI is in one or two panes
  private static final String ARGUMENT_TWO_PANE_ENABLED = "ARGUMENT_TWO_PANE_ENABLED";
  private FragmentRecipeStepListBinding binding;
  private boolean twoPaneEnabled;


  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public RecipeStepListFragment() {
    // Mandatory empty constructor
  }


  public static RecipeStepListFragment newInstance(final Recipe recipe,
      final boolean twoPaneEnabled) {
    final RecipeStepListFragment fragment = new RecipeStepListFragment();
    final Bundle args = new Bundle();

    args.putParcelable(Recipe.ARGUMENT_SELECTED_RECIPE, Parcels.wrap(recipe));
    args.putBoolean(ARGUMENT_TWO_PANE_ENABLED, twoPaneEnabled);

    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = FragmentRecipeStepListBinding.inflate(inflater, container, false);

    final Bundle arguments = Objects.requireNonNull(getArguments());
    if (arguments.containsKey(Recipe.ARGUMENT_SELECTED_RECIPE)
        && arguments.containsKey(ARGUMENT_TWO_PANE_ENABLED)) {
      // Get the flag for UI's panes
      twoPaneEnabled = arguments.getBoolean(ARGUMENT_TWO_PANE_ENABLED);
      // Get the Recipe specified by the fragment arguments.
      final Recipe recipe = Parcels
          .unwrap(getArguments().getParcelable(Recipe.ARGUMENT_SELECTED_RECIPE));

      if (recipe != null) {
        setupRecyclerViewStepList(recipe);
      }
    }
    return binding.getRoot();
  }

  private void setupRecyclerViewStepList(@NonNull final Recipe recipe) {

    binding.recyclerviewRecipeStepList.setHasFixedSize(true);
    final RecipeStepItemAdapter itemAdapter =
        new RecipeStepItemAdapter(getActivity(), twoPaneEnabled);
    itemAdapter.setItemList(recipe.getSteps());
    binding.recyclerviewRecipeStepList.setAdapter(itemAdapter);
  }
}

