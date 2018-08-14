package com.ampieguillermo.bakingforall.utils;

import android.support.v4.util.ArrayMap;
import com.ampieguillermo.bakingforall.R;

// TODO: 8/14/18 Move the logic of this class to a Database that
// contains: (recipe_name, drawable_id) mapping
public enum RecipePhotoAssets {
  ;

  private static final ArrayMap<String, Integer> assets = new ArrayMap<String, Integer>() {{
    put("Nutella Pie", R.drawable.nutella_pie);
    put("Brownies", R.drawable.brownies);
    put("Yellow Cake", R.drawable.yellow_cake);
    put("Cheesecake", R.drawable.cheesecake);
  }};

  public static int getPhotoAsset(final String recipeName) {
    return assets.get(recipeName);
  }
}
