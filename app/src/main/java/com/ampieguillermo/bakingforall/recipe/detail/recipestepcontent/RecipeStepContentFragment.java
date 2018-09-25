package com.ampieguillermo.bakingforall.recipe.detail.recipestepcontent;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
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
import com.google.android.exoplayer2.C;
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
 *
 * Note:
 * - ExoPlayer setup code from: https://google.github.io/ExoPlayer/guide.html
 * - ExoPlayer saved state code from: https://google.github.io/ExoPlayer/demo-application.html
 */
public class RecipeStepContentFragment extends Fragment {

  //
  // Keys for Fragment Arguments
  //

  // Flag to indicate if the UI is in one or two panes
  public static final String ARGUMENT_TWO_PANE_ENABLED = "ARGUMENT_TWO_PANE_ENABLED";
  // The Recipe step used as a Fragment argument
  private static final String ARGUMENT_SELECTED_RECIPE_STEP = "ARGUMENT_SELECTED_RECIPE_STEP";

  // M: Marshmallow --> API level 23
  private static final int MARSHMALLOW = Build.VERSION_CODES.M;

  //
  // Keys for Bundles
  //
  private static final String BUNDLE_PLAYBACK_POSITION = "BUNDLE_PLAYBACK_POSITION";

  private FragmentRecipeStepContentBinding binding;
  private SimpleExoPlayer exoPlayer;
  private Uri recipeStepVideoUri;
  private long playbackPosition;
  private boolean twoPaneEnabled;

  private int screenOrientation;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public RecipeStepContentFragment() {
    // Mandatory empty constructor
  }

  public static RecipeStepContentFragment newInstance(final RecipeStep recipeStep,
      final boolean twoPaneEnabled) {
    final RecipeStepContentFragment fragment = new RecipeStepContentFragment();
    final Bundle args = new Bundle();

    args.putParcelable(ARGUMENT_SELECTED_RECIPE_STEP, Parcels.wrap(recipeStep));
    args.putBoolean(ARGUMENT_TWO_PANE_ENABLED, twoPaneEnabled);
    fragment.setArguments(args);
    return fragment;
  }

  /**
   * Called to do initial creation of a fragment.  This is called after
   * {@link #onAttach(Activity)} and before
   * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
   *
   * <p>Note that this can be called while the fragment's activity is
   * still in the process of being created.  As such, you can not rely
   * on things like the activity's content view hierarchy being initialized
   * at this point.  If you want to do work once the activity itself is
   * created, see {@link #onActivityCreated(Bundle)}.
   *
   * <p>Any restored child fragments will be created before the base
   * <code>Fragment.onCreate</code> method returns.</p>
   *
   * @param savedInstanceState If the fragment is being re-created from
   * a previous saved state, this is the state.
   */
  @Override
  public void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null) {
      playbackPosition = C.TIME_UNSET;
    } else {
      playbackPosition = savedInstanceState.getLong(BUNDLE_PLAYBACK_POSITION);
    }
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    // Setup the DataBinding access
    binding = FragmentRecipeStepContentBinding.inflate(inflater, container, false);

    final Bundle arguments = Objects.requireNonNull(getArguments());
    if (arguments.containsKey(ARGUMENT_SELECTED_RECIPE_STEP)
        && arguments.containsKey(ARGUMENT_TWO_PANE_ENABLED)) {
      // Get the flag for UI's panes
      twoPaneEnabled = arguments.getBoolean(ARGUMENT_TWO_PANE_ENABLED);
      // Get the RecipeStep specified by the fragment arguments.
      final RecipeStep recipeStep =
          Parcels.unwrap(arguments.getParcelable(ARGUMENT_SELECTED_RECIPE_STEP));

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
          recipeStepVideoUri = Uri.parse(StringUtils.isEmpty(videoUrl) ? thumbnailUrl : videoUrl);
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
   * Called when the Fragment is visible to the user.  This is generally
   * tied to {@link Activity#onStart() Activity.onStart} of the containing
   * Activity's lifecycle.
   */
  @Override
  public void onStart() {
    super.onStart();

    if (Util.SDK_INT > MARSHMALLOW) {
      initializePlayer();
    }
  }

  /**
   * Called when the fragment is visible to the user and actively running.
   * This is generally
   * tied to {@link Activity#onResume() Activity.onResume} of the containing
   * Activity's lifecycle.
   */
  @Override
  public void onResume() {
    super.onResume();

    if (!twoPaneEnabled) { // TODO: 9/24/18 Call hideSystemUi when in one pane only and landscape
      hideSystemUi();
    }
    if ((Util.SDK_INT <= MARSHMALLOW) || (exoPlayer == null)) {
      initializePlayer();
    }
  }

  /**
   * Called when the Fragment is no longer resumed.  This is generally
   * tied to {@link Activity#onPause() Activity.onPause} of the containing
   * Activity's lifecycle.
   */
  @Override
  public void onPause() {
    super.onPause();

    if (Util.SDK_INT <= MARSHMALLOW) {
      releasePlayer();
    }
  }

  /**
   * Called when the Fragment is no longer started.  This is generally
   * tied to {@link Activity#onStop() Activity.onStop} of the containing
   * Activity's lifecycle.
   */
  @Override
  public void onStop() {
    super.onStop();

    if (Util.SDK_INT > MARSHMALLOW) {
      releasePlayer();
    }
  }

  /**
   * Called to ask the fragment to save its current dynamic state, so it
   * can later be reconstructed in a new instance of its process is
   * restarted.  If a new instance of the fragment later needs to be
   * created, the data you place in the Bundle here will be available
   * in the Bundle given to {@link #onCreate(Bundle)},
   * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}, and
   * {@link #onActivityCreated(Bundle)}.
   *
   * <p>This corresponds to {@link Activity#onSaveInstanceState(Bundle)
   * Activity.onSaveInstanceState(Bundle)} and most of the discussion there
   * applies here as well.  Note however: <em>this method may be called
   * at any time before {@link #onDestroy()}</em>.  There are many situations
   * where a fragment may be mostly torn down (such as when placed on the
   * back stack with no UI showing), but its state will not be saved until
   * its owning activity actually needs to save its state.
   *
   * @param outState Bundle in which to place your saved state.
   */
  @Override
  public void onSaveInstanceState(@NonNull final Bundle outState) {
    outState.putLong(BUNDLE_PLAYBACK_POSITION, playbackPosition);
  }

  private void showNoVideo() {
    binding.playerviewRecipeStepContent.setVisibility(View.INVISIBLE); // Hide ExoPlayer
    binding.imageviewRecipeStepContent.setVisibility(View.VISIBLE); // Show a default image
  }

  /**
   * Setup the ExoPlayer
   */
  private void initializePlayer() {
    final Activity activity = Objects.requireNonNull(getActivity());
    // 1. Create a default TrackSelector
    final DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
    if (exoPlayer == null) {
      //
      // Create an instance of the ExoPlayer.
      //
      final TrackSelection.Factory videoTrackSelectionFactory =
          new AdaptiveTrackSelection.Factory(defaultBandwidthMeter);
      final DefaultTrackSelector trackSelector =
          new DefaultTrackSelector(videoTrackSelectionFactory);

      // 2. Create the player
      exoPlayer = ExoPlayerFactory.newSimpleInstance(activity, trackSelector);

      // 3. Bind the player to the view.
      binding.playerviewRecipeStepContent.setPlayer(exoPlayer);
    }
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

    // Set player position in the media
    exoPlayer.seekTo(playbackPosition);
    // Prepare the player with the media source.
    exoPlayer.prepare(videoSource, false, false);

    // Auto-play the video!
    // TODO: 9/16/18 Handle a preference for autoPlay videos
    // TODO: 9/16/18 Cache the videos!
    exoPlayer.setPlayWhenReady(true);

    if (!twoPaneEnabled) { // TODO: 9/24/18 Call hideSystemUi when in one pane only and lanscape
      hideSystemUi();
    }
  }

  private void releasePlayer() {
    if (exoPlayer != null) {
      playbackPosition = Math.max(0, exoPlayer.getCurrentPosition());
      exoPlayer.stop();
      exoPlayer.release();
      exoPlayer = null;
    }
  }

  //  @SuppressLint("InlinedApi")
  private void hideSystemUi() {
    binding.playerviewRecipeStepContent.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
        | View.SYSTEM_UI_FLAG_FULLSCREEN
        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
  }
}

