package com.ampieguillermo.bakingforall.recipe.detail.recipestepcontent;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ampieguillermo.bakingforall.R;
import com.ampieguillermo.bakingforall.model.RecipeStep;
import java.util.Objects;
import org.parceler.Parcels;

/**
 * A placeholder fragment to show a Recipe step details: Accompanying video
 * and detailed instructions.
 */
public class RecipeStepContentFragment extends Fragment {

  public RecipeStepContentFragment() {
  }

  public static RecipeStepContentFragment newInstance(final RecipeStep recipeStep) {
    final RecipeStepContentFragment fragment = new RecipeStepContentFragment();
    final Bundle args = new Bundle();

    args.putParcelable(RecipeStep.ARGUMENT_SELECTED_RECIPE_STEP, Parcels.wrap(recipeStep));
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View rootView = inflater.inflate(R.layout.fragment_recipe_step_content, container, false);

    if (Objects.requireNonNull(getArguments()).containsKey(RecipeStep.ARGUMENT_SELECTED_RECIPE_STEP)) {
      // Get the RecipeStep specified by the fragment arguments.
      final RecipeStep recipeStep =
          Parcels.unwrap(getArguments().getParcelable(RecipeStep.ARGUMENT_SELECTED_RECIPE_STEP));
      if (recipeStep != null) {
        final CollapsingToolbarLayout appBarLayout =
            Objects.requireNonNull(getActivity())
                .findViewById(R.id.ctoolbarlayout_recipe_step_content);

        // Set the recipe step's short description as title
        if (appBarLayout != null) {
          appBarLayout.setTitle(recipeStep.getShortDescription());
        }
        final TextView textViewDescription =
            rootView.findViewById(R.id.textview_recipe_step_content_long_description);
        textViewDescription.setText(recipeStep.getDescription());
      }
    }
    return rootView;
  }
}
