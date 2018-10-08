package com.ampieguillermo.bakingforall.recipe.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.ampieguillermo.bakingforall.R;
import com.ampieguillermo.bakingforall.databinding.ItemRecipeStepListBinding;
import com.ampieguillermo.bakingforall.model.RecipeStep;
import com.ampieguillermo.bakingforall.recipe.detail.RecipeStepItemAdapter.RecipeStepViewHolder;
import com.ampieguillermo.bakingforall.recipe.detail.recipestepcontent.RecipeStepContentActivity;
import com.ampieguillermo.bakingforall.recipe.detail.recipestepcontent.RecipeStepContentFragment;
import java.util.Collections;
import java.util.List;

public class RecipeStepItemAdapter extends RecyclerView.Adapter<RecipeStepViewHolder> {

  /* package */ static final String LOG_TAG = RecipeStepItemAdapter.class.getSimpleName();

  /* package */ final FragmentActivity mParentActivity;
  /* package */ final boolean mTwoPane;
  /* package */ List<RecipeStep> recipeStepList;

  // RecyclerView highlight selected item reference code: https://code.i-harness.com/en/q/19ef2bc
  //
  // To save the selected item's position
  /* package */ int selection = RecyclerView.NO_POSITION;

  private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      final int position = (int) view.getTag();
      final RecipeStep recipeStep = recipeStepList.get(position);

      // Handle the selection
      notifyItemChanged(selection);
      selection = position; // update the selection
      notifyItemChanged(selection);

      if (mTwoPane) { // Tablet case
        final RecipeStepContentFragment fragment = RecipeStepContentFragment
            .newInstance(recipeStep, mTwoPane);
        mParentActivity.getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.recipe_step_content_container, fragment)
            .commit();
      } else { // Phone case
        final Context context = view.getContext();
        context.startActivity(RecipeStepContentActivity.getStartIntent(context, recipeStep));
      }
    }
  };


  public RecipeStepItemAdapter(final FragmentActivity parentActivity, final boolean twoPane) {
    recipeStepList = Collections.emptyList();
    mParentActivity = parentActivity;
    mTwoPane = twoPane;
  }

  /**
   * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
   * an item.
   * <p>
   * This new ViewHolder should be constructed with a new View that can represent the items
   * of the given type. You can either create a new View manually or inflate it from an XML
   * layout file.
   * <p>
   * The new ViewHolder will be used to display items of the adapter using
   * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
   * different items in the data set, it is a good idea to cache references to sub views of
   * the View to avoid unnecessary {@link View#findViewById(int)} calls.
   *
   * @param parent The ViewGroup into which the new View will be added after it is bound to
   * an adapter position.
   * @param viewType The view type of the new View.
   * @return A new ViewHolder that holds a View of the given view type.
   * @see #getItemViewType(int)
   * @see #onBindViewHolder(ViewHolder, int)
   */
  @NonNull
  @Override
  public RecipeStepViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
      final int viewType) {
    final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    final View view = inflater.inflate(R.layout.item_recipe_step_list, parent, false);

    return new RecipeStepViewHolder(view, mOnClickListener);
  }

  /**
   * Called by RecyclerView to display the data at the specified position. This method should
   * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
   * position.
   * <p>
   * Note that unlike {@link ListView}, RecyclerView will not call this method
   * again if the position of the item changes in the data set unless the item itself is
   * invalidated or the new position cannot be determined. For this reason, you should only
   * use the <code>position</code> parameter while acquiring the related data item inside
   * this method and should not keep a copy of it. If you need the position of an item later
   * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
   * have the updated adapter position.
   *
   * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
   * handle efficient partial bind.
   *
   * @param holder The ViewHolder which should be updated to represent the contents of the
   * item at the given position in the data set.
   * @param position The position of the item within the adapter's data set.
   */
  @Override
  public void onBindViewHolder(@NonNull final RecipeStepViewHolder holder, final int position) {
    holder.setupItemView(recipeStepList.get(position), selection);
  }

  /**
   * Returns the total number of items in the data set held by the adapter.
   *
   * @return The total number of items in this adapter.
   */
  @Override
  public int getItemCount() {
    return recipeStepList.size();
  }

  public void setItemList(@NonNull final List<RecipeStep> list) {
    recipeStepList = Collections.unmodifiableList(list);
    notifyDataSetChanged();
  }

  /* package */ static class RecipeStepViewHolder extends RecyclerView.ViewHolder {

    private final ItemRecipeStepListBinding binding;

    /* package */ RecipeStepViewHolder(final View view, final View.OnClickListener listener) {
      super(view);
      // The layout is already inflated(in onCreateViewHolder() method), so we need to "bind" it now
      binding = ItemRecipeStepListBinding.bind(view);
      itemView.setOnClickListener(listener);
    }

    /* package */ void setupItemView(final RecipeStep recipeStep, final int selection) {
      binding.textviewItemStepListShortDescription.setText(recipeStep.getShortDescription());

      // If there is no video for this step --> show an ellipsis icon ("more horizontal" icon)
      // instead of the "play icon"
      final int resId = recipeStep.hasVideo() ? R.drawable.ic_play_circle_outline_black_24dp
          : R.drawable.ic_more_horiz_black_24dp;
      binding.imageviewRecipeStepContentPlayIcon.setImageResource(resId);

      final int position = getAdapterPosition();
      itemView.setTag(position);

      // Handle the selection
       itemView.setSelected(selection == position);
    }
  }
}


