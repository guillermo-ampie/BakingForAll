package com.ampieguillermo.bakingforall.utils;

import android.support.v4.util.ArrayMap;
import com.ampieguillermo.bakingforall.R;
import java.util.Locale;

// TODO: 8/14/18 Move the logic of this class to a Database that
// contains: (recipe_name, drawable_id) mapping
public enum RecipeAssets {
  ;

  // The Ingredient's measure is used as key, not the best approach but it is what we got...
  public static final ArrayMap<String, Integer> DRAWABLE_ASSETS_MAP = new ArrayMap<>();
  // The Recipe's name is used as key, not the best approach but it is what we got...
  private static final ArrayMap<String, Integer> PHOTO_ASSETS_MAP = new ArrayMap<>();

  static {
    PHOTO_ASSETS_MAP.put("CHEESECAKE", R.drawable.cheesecake);
    PHOTO_ASSETS_MAP.put("YELLOW CAKE", R.drawable.yellow_cake);
    PHOTO_ASSETS_MAP.put("BROWNIES", R.drawable.brownies);
    PHOTO_ASSETS_MAP.put("NUTELLA PIE", R.drawable.nutella_pie);

    DRAWABLE_ASSETS_MAP.put("CUP", R.drawable.ic_measure_cup_24dp);
    DRAWABLE_ASSETS_MAP.put("G", R.drawable.ic_scale_24dp);
    DRAWABLE_ASSETS_MAP.put("K", R.drawable.ic_scale_24dp);
    DRAWABLE_ASSETS_MAP.put("OZ", R.drawable.ic_scale_24dp);
    DRAWABLE_ASSETS_MAP.put("TBLSP", R.drawable.ic_tablespoon_24dp);
    DRAWABLE_ASSETS_MAP.put("TSP", R.drawable.ic_spoon_24dp);
    DRAWABLE_ASSETS_MAP.put("UNIT", R.drawable.ic_unit_circle_24dp);
  }

  public static int getPhotoAsset(final String recipeName) {
    final Integer result = PHOTO_ASSETS_MAP.get(recipeName.toUpperCase(Locale.US));
    return (result != null) ? result : R.drawable.ic_broken_image_black_180dp;
  }

  public static int getDrawableAsset(final String measure) {
    final Integer result = DRAWABLE_ASSETS_MAP.get(measure.toUpperCase(Locale.US));
    return (result != null) ? result : R.drawable.ic_ingredient_24dp;
  }
}

