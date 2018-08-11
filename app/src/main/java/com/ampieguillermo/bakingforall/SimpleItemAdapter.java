package com.ampieguillermo.bakingforall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ampieguillermo.bakingforall.SimpleItemAdapter.SimpleItemViewHolder;
import com.ampieguillermo.bakingforall.model.Recipe;
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
      if (mTwoPane) {
        final Bundle arguments = new Bundle();
        arguments.putString(RecipeDetailFragment.ARG_ITEM_ID,
            Integer.toString(getItem(position).getId()));
        final RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(arguments);
        mParentActivity
            .getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.recipe_detail_container, fragment)
            .commit();
      } else {
        final Context context = view.getContext();
        final Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID,
            Integer.toString(getItem(position).getId()));

        context.startActivity(intent);
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

    private final TextView mIdView;
    private final TextView mContentView;

    SimpleItemViewHolder(final View view, final View.OnClickListener listener) {
      super(view);
      mIdView = view.findViewById(R.id.textview_recipe_list_id);
      mContentView = view.findViewById(R.id.textview_recipe_list_content);
      itemView.setOnClickListener(listener);
    }

    void setupItemView(final Recipe recipe) {
      mIdView.setText(String.format("<%d> ", recipe.getId()));
      mContentView.setText(recipe.getName());
      itemView.setTag(getAdapterPosition());
    }
  }
}
