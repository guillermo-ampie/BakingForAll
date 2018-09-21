package com.ampieguillermo.bakingforall.recipe.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ampieguillermo.bakingforall.R;
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
  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public RecipeStepListFragment() {
    // Mandatory empty constructor
  }


  public static RecipeStepListFragment newInstance(final Recipe recipe) {
    final RecipeStepListFragment fragment = new RecipeStepListFragment();
    final Bundle args = new Bundle();

    args.putParcelable(Recipe.ARGUMENT_SELECTED_RECIPE, Parcels.wrap(recipe));
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    final View rootView =
        inflater.inflate(R.layout.fragment_recipe_step_list, container, false);

    if (Objects.requireNonNull(getArguments()).containsKey(Recipe.ARGUMENT_SELECTED_RECIPE)) {
      // Get the Recipe specified by the fragment arguments.
      final Recipe recipe = Parcels
          .unwrap(getArguments().getParcelable(Recipe.ARGUMENT_SELECTED_RECIPE));

      if (recipe != null) {
        final Toolbar toolbar = rootView.findViewById(R.id.toolbar_recipe_step_content);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        final CollapsingToolbarLayout appBarLayout =
            Objects.requireNonNull(getActivity()).findViewById(R.id.ctoolbarlayout_recipe_detail);
        if (appBarLayout != null) {
          appBarLayout.setTitle(recipe.getName());
        }
        final RecyclerView recyclerViewStepList =
            rootView.findViewById(R.id.recyclerview_recipe_step_list);
        Objects.requireNonNull(recyclerViewStepList);
        setupRecyclerViewStepList(recipe, recyclerViewStepList);
      }
    }
    return rootView;
  }

  private void setupRecyclerViewStepList(@NonNull final Recipe recipe,
      @NonNull final RecyclerView recyclerViewStepList) {

    recyclerViewStepList.setHasFixedSize(true);
    final RecipeStepItemAdapter itemAdapter = new RecipeStepItemAdapter(getActivity(),false);
    itemAdapter.setItemList(recipe.getSteps());
    recyclerViewStepList.setAdapter(itemAdapter);
  }
}

