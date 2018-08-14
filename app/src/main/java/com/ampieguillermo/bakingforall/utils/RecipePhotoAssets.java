package com.ampieguillermo.bakingforall.utils;

import android.support.v4.util.ArrayMap;
import com.ampieguillermo.bakingforall.R;

// TODO: 8/14/18 Move the logic of this class to a Database that
// contains: (recipe_name, drawable_id) mapping
public enum RecipePhotoAssets {
  ;

  private static final ArrayMap<String, Integer> ASSETS_MAP = new ArrayMap<>();

  static {
    ASSETS_MAP.put("Cheesecake", R.drawable.cheesecake);
    ASSETS_MAP.put("Yellow Cake", R.drawable.yellow_cake);
    ASSETS_MAP.put("Brownies", R.drawable.brownies);
    ASSETS_MAP.put("Nutella Pie", R.drawable.nutella_pie);
  }

  public static int getPhotoAsset(final String recipeName) {
    return ASSETS_MAP.get(recipeName);
  }
}
