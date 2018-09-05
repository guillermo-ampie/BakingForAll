package com.ampieguillermo.bakingforall.recipe.detail.recipestepcontent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.ampieguillermo.bakingforall.R;
import com.ampieguillermo.bakingforall.model.RecipeStep;
import com.ampieguillermo.bakingforall.recipe.detail.RecipeDetailActivity;
import org.parceler.Parcels;

public class RecipeStepContentActivity extends AppCompatActivity {

  private static final String LOG_TAG = RecipeStepContentActivity.class.getSimpleName();
  private static final String EXTRA_RECIPE_STEP =
      "com.ampieguillermo.bakingforall.recipe.detail.recipestepcontent.EXTRA_RECIPE_STEP";

  public static Intent getStartIntent(final Context context, final RecipeStep recipeStep) {
    final Intent intent = new Intent(context, RecipeDetailActivity.class);

    intent.putExtra(EXTRA_RECIPE_STEP, Parcels.wrap(recipeStep));
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_step_content);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

}
