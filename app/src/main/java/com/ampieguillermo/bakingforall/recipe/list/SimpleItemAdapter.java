package com.ampieguillermo.bakingforall.recipe.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ampieguillermo.bakingforall.R;
import com.ampieguillermo.bakingforall.recipe.detail.RecipeDetailActivity;
import com.ampieguillermo.bakingforall.recipe.detail.IngredientListFragment;
import com.ampieguillermo.bakingforall.recipe.list.SimpleItemAdapter.SimpleItemViewHolder;
import com.ampieguillermo.bakingforall.model.Recipe;
import com.ampieguillermo.bakingforall.utils.RecipeAssets;
import java.util.List;
import java.util.Objects;

public class SimpleItemAdapter extends ListAdapter<Recipe, SimpleItemViewHolder> {

  private static final String LOG_TAG = SimpleItemAdapter.class.getSimpleName();
  private static final DiffUtil.ItemCallback<Recipe> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<Recipe>() {
        @Override
        public boolean areItemsTheSame(Recipe oldItem, Recipe newItem) {
          return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(Recipe oldItem, Recipe newItem) {
          return ((Integer) oldItem.getId()).equals(newItem.getId());
        }
      };

  /* package */ final RecipeListActivity mParentActivity;
  /* package */ final boolean mTwoPane;
  private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      final int position = (int) view.getTag();
      final Recipe recipe = getItem(position);

      if (mTwoPane) {
        final IngredientListFragment fragment = IngredientListFragment.newInstance(recipe);
        mParentActivity.getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.recipe_detail_container, fragment)
            .commit();
      } else {
        final Context context = view.getContext();
        context.startActivity(RecipeDetailActivity.getStartIntent(context, recipe));
      }
    }
  };

  SimpleItemAdapter(RecipeListActivity parent,
      boolean twoPane) {
    super(DIFF_CALLBACK);
    mParentActivity = parent;
    mTwoPane = twoPane;
  }

  public void setItemList(final List<Recipe> itemList) {
    submitList(itemList);
  }

  @NonNull
  @Override
  public SimpleItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_recipe_list, parent, false);
    return new SimpleItemViewHolder(view, mOnClickListener);
  }

  @Override
  public void onBindViewHolder(@NonNull final SimpleItemViewHolder holder, int position) {
    holder.setupItemView(getItem(position));
  }

  /* package */ static class SimpleItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView textRecipeName;
    private final ImageView imageRecipePhoto;

    SimpleItemViewHolder(final View view, final View.OnClickListener listener) {
      super(view);
      textRecipeName = view.findViewById(R.id.textview_recipe_list_recipe_name);
      imageRecipePhoto = view.findViewById(R.id.imageview_recipe_list_recipe_photo);
      itemView.setOnClickListener(listener);
    }

    void setupItemView(final Recipe recipe) {
      final String recipeName = recipe.getName();
      textRecipeName.setText(String.format("  %s  ", recipeName));
      imageRecipePhoto.setImageResource(RecipeAssets.getPhotoAsset(recipeName));
      itemView.setTag(getAdapterPosition());
    }
  }
}
