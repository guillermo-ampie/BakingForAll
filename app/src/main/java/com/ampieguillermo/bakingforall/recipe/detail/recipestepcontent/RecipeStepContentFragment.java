package com.ampieguillermo.bakingforall.recipe.detail.recipestepcontent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ampieguillermo.bakingforall.R;
import com.ampieguillermo.bakingforall.model.Recipe;
import com.ampieguillermo.bakingforall.model.RecipeStep;
import org.parceler.Parcels;

/**
 * A placeholder fragment to show a Recipe step details: Accompanying video
 * and detailed instructions.
 */
public class RecipeStepContentFragment extends Fragment {

  public static RecipeStepContentFragment newInstance(final RecipeStep recipeStep) {
    final RecipeStepContentFragment fragment = new RecipeStepContentFragment();
    final Bundle args = new Bundle();

    args.putParcelable(Recipe.ARGUMENT_SELECTED_RECIPE, Parcels.wrap(recipeStep));
    fragment.setArguments(args);
    return fragment;
  }
  public RecipeStepContentFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_recipe_step_content, container, false);
  }
}
