package com.ampieguillermo.bakingforall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ampieguillermo.bakingforall.SimpleItemAdapter.SimpleItemViewHolder;
import com.ampieguillermo.bakingforall.dummy.DummyContent.DummyItem;
import java.util.List;

public class SimpleItemAdapter
    extends RecyclerView.Adapter<SimpleItemViewHolder> {

  /* package */ final RecipeListActivity mParentActivity;
  /* package */ final List<DummyItem> mValues;
  /* package */ final boolean mTwoPane;
  private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      final int position = (int) view.getTag();
      if (mTwoPane) {
        final Bundle arguments = new Bundle();
        arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, mValues.get(position).id);
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
        intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, mValues.get(position).id);

        context.startActivity(intent);
      }
    }
  };

  SimpleItemAdapter(RecipeListActivity parent,
      List<DummyItem> items,
      boolean twoPane) {
    mValues = items;
    mParentActivity = parent;
    mTwoPane = twoPane;
  }

  @Override
  public SimpleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_recipe_list, parent, false);
    return new SimpleItemViewHolder(view, mOnClickListener);
  }

  @Override
  public void onBindViewHolder(@NonNull final SimpleItemViewHolder holder, int position) {
    holder.mIdView.setText(mValues.get(position).id);
    holder.mContentView.setText(mValues.get(position).content);

    holder.itemView.setTag(position);
  }

  @Override
  public int getItemCount() {
    return mValues.size();
  }

    /* package */ static class SimpleItemViewHolder extends RecyclerView.ViewHolder {

    /* package */ final TextView mIdView;
    /* package */ final TextView mContentView;

    SimpleItemViewHolder(View view, final View.OnClickListener listener) {
      super(view);
      mIdView = view.findViewById(R.id.textview_recipe_list_id);
      mContentView = view.findViewById(R.id.textview_recipe_list_content);
      itemView.setOnClickListener(listener);
    }
  }
}
