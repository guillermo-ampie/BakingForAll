package com.ampieguillermo.bakingforall.recipe.detail.recipestepcontent;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ampieguillermo.bakingforall.R;
import com.ampieguillermo.bakingforall.databinding.FragmentRecipeStepContentBinding;
import com.ampieguillermo.bakingforall.model.RecipeStep;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Util;
import java.util.Objects;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.parceler.Parcels;

/**
 * A placeholder fragment to show a Recipe step details: Accompanying video
 * and detailed instructions.
 */
public class RecipeStepContentFragment extends Fragment {

  private FragmentRecipeStepContentBinding binding;
  private SimpleExoPlayer mExoPlayer;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public RecipeStepContentFragment() {
    // Mandatory empty constructor
  }

  public static RecipeStepContentFragment newInstance(final RecipeStep recipeStep) {
    final RecipeStepContentFragment fragment = new RecipeStepContentFragment();
    final Bundle args = new Bundle();

    args.putParcelable(RecipeStep.ARGUMENT_SELECTED_RECIPE_STEP, Parcels.wrap(recipeStep));
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    // Setup the DataBinding access
    binding = FragmentRecipeStepContentBinding.inflate(inflater, container, false);

    if (Objects.requireNonNull(getArguments())
        .containsKey(RecipeStep.ARGUMENT_SELECTED_RECIPE_STEP)) {
      // Get the RecipeStep specified by the fragment arguments.
      final RecipeStep recipeStep =
          Parcels.unwrap(getArguments().getParcelable(RecipeStep.ARGUMENT_SELECTED_RECIPE_STEP));
      if (recipeStep != null) {
        final CollapsingToolbarLayout appBarLayout =
            Objects.requireNonNull(getActivity())
                .findViewById(R.id.ctoolbarlayout_recipe_step_content);

        // Set the recipe step's short description as title
        if (appBarLayout != null) {
          appBarLayout.setTitle(recipeStep.getShortDescription());
        }

        binding.textviewRecipeStepContentLongDescription.setText(recipeStep.getDescription());

        final String videoUrl = recipeStep.getVideoUrl();
        final String thumbnailUrl = recipeStep.getThumbnailUrl();
        if (StringUtils.isEmpty(videoUrl)
            && StringUtils.isEmpty(thumbnailUrl)) {
          // There is no video for this recipe step
          showNoVideo();
        } else {

          // The accompanying video URL can come in the "videoURL" or "thumbnailURL" field
          final Uri recipeStepVideoUri =
              Uri.parse(StringUtils.isEmpty(videoUrl) ? thumbnailUrl : videoUrl);

          // Play video!
          setupExoPlayer(recipeStepVideoUri);
        }
      } else {
        showNoVideo();
      }
    } else {
      showNoVideo();
    }
    return binding.getRoot();
  }

  /**
   * Called when the fragment is no longer in use.  This is called
   * after {@link #onStop()} and before {@link #onDetach()}.
   */
  @Override
  public void onDestroy() {
    super.onDestroy();
    releasePlayer();
  }

  private void showNoVideo() {
    binding.playerviewRecipeStepContent.setVisibility(View.INVISIBLE); // Hide ExoPlayer
    binding.imageviewRecipeStepContent.setVisibility(View.VISIBLE); // Show a default image
  }

  private void setupExoPlayer(final Uri recipeStepVideoUri) {
    // Note: ExoPlayer setup code from: https://google.github.io/ExoPlayer/guide.html

//    if (mExoPlayer == null) {
    // Create an instance of the ExoPlayer.

    final Activity activity = Objects.requireNonNull(getActivity());
    // 1. Create a default TrackSelector
    final DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
    final TrackSelection.Factory videoTrackSelectionFactory =
        new AdaptiveTrackSelection.Factory(defaultBandwidthMeter);
    final DefaultTrackSelector trackSelector =
        new DefaultTrackSelector(videoTrackSelectionFactory);

    // 2. Create the player
    mExoPlayer = ExoPlayerFactory.newSimpleInstance(activity, trackSelector);

    // 3. Bind the player to the view.
    binding.playerviewRecipeStepContent.setPlayer(mExoPlayer);

    // 4. Playing a media from the Internet (Uri):

    // Produces DataSource instances through which media data is loaded.
    // Note: Using ExoPlayer OkHttp extension: The OkHttp extension is an HttpDataSource
    // implementation using Square's OkHttp(https://square.github.io/okhttp/).
    final DataSource.Factory dataSourceFactory = new OkHttpDataSourceFactory(new OkHttpClient(),
        Util.getUserAgent(activity, getString(R.string.app_name)),
        defaultBandwidthMeter); // Measures bandwidth during playback. Can be null if not required.

    // Make a a MediaSource to play
    final MediaSource videoSource =
        new ExtractorMediaSource.Factory(dataSourceFactory)
            .createMediaSource(recipeStepVideoUri);

    // Prepare the player with the media source.
    mExoPlayer.prepare(videoSource);

    // Auto-play the video!
    // TODO: 9/16/18 Handle a preference for autoPlay videos
    // TODO: 9/16/18 Cache the videos!
    mExoPlayer.setPlayWhenReady(true);
//    }
  }

  private void releasePlayer() {
    if (mExoPlayer != null) {
      mExoPlayer.stop();
      mExoPlayer.release();
//      mExoPlayer = null;
    }
  }
}

