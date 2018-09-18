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
import com.ampieguillermo.bakingforall.model.Recipe;
import com.ampieguillermo.bakingforall.recipe.detail.RecipeDetailActivity;
import com.ampieguillermo.bakingforall.recipe.list.RecipeItemAdapter.RecipeItemViewHolder;
import com.ampieguillermo.bakingforall.utils.RecipeAssets;
import java.util.List;
import java.util.Objects;

public class RecipeItemAdapter extends ListAdapter<Recipe, RecipeItemViewHolder> {

  private static final String LOG_TAG = RecipeItemAdapter.class.getSimpleName();
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

  private final View.OnClickListener mOnClickListener = this::onClick;

  RecipeItemAdapter() {
    super(DIFF_CALLBACK);
  }

  public void setItemList(final List<Recipe> itemList) {
    submitList(itemList);
  }

  @NonNull
  @Override
  public RecipeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_recipe_list, parent, false);
    return new RecipeItemViewHolder(view, mOnClickListener);
  }

  @Override
  public void onBindViewHolder(@NonNull final RecipeItemViewHolder holder, int position) {
    holder.setupItemView(getItem(position));
  }

  private void onClick(View view) {
    final int position = (int) view.getTag();
    final Recipe recipe = getItem(position);
    final Context context = view.getContext();

    context.startActivity(RecipeDetailActivity.getStartIntent(context, recipe));
  }

  /* package */ static class RecipeItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView textRecipeName;
    private final ImageView imageRecipePhoto;

    RecipeItemViewHolder(final View view, final View.OnClickListener listener) {
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
