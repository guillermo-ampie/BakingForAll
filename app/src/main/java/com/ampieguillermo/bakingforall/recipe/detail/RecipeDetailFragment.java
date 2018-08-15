package com.ampieguillermo.bakingforall.recipe.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ampieguillermo.bakingforall.R;
import com.ampieguillermo.bakingforall.model.Recipe;
import com.ampieguillermo.bakingforall.recipe.list.RecipeListActivity;
import java.util.Objects;
import org.parceler.Parcels;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment {

  private static final String LOG_TAG = RecipeDetailFragment.class.getSimpleName();

  /**
   * The fragment argument representing the Recipe that this fragment will handle
   */
  private static final String ARGUMENT_SELECTED_RECIPE = "ARGUMENT_SELECTED_RECIPE";

  private Recipe recipe;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public RecipeDetailFragment() {
    // Mandatory empty constructor
  }


  public static RecipeDetailFragment newInstance(final Recipe recipe) {
    final RecipeDetailFragment fragment = new RecipeDetailFragment();
    final Bundle args = new Bundle();

    args.putParcelable(ARGUMENT_SELECTED_RECIPE, Parcels.wrap(recipe));
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (Objects.requireNonNull(getArguments()).containsKey(ARGUMENT_SELECTED_RECIPE)) {
      // Get the Recipe specified by the fragment arguments.
      recipe = Parcels.unwrap(getArguments().getParcelable(ARGUMENT_SELECTED_RECIPE));

      final Activity activity = getActivity();
      final CollapsingToolbarLayout appBarLayout = Objects.requireNonNull(activity)
          .findViewById(R.id.ctoolbarlayout_recipe_detail);
      if (appBarLayout != null) {
        appBarLayout.setTitle(recipe.getName());
      }
    }
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View rootView =
        inflater.inflate(R.layout.fragment_recipe_detail, container, false);

    // Show the dummy content as text in a TextView.
    if (recipe != null) {
      ((TextView) rootView.findViewById(R.id.textview_recipe_detail))
          .setText(recipe.getIngredients().toString());
    }
    return rootView;
  }
}
