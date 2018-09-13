package com.ampieguillermo.bakingforall.model;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.parceler.Parcel;

/**
 * class RecipeStep: Represents a step when preparing a recipe
 * POJO class created with: http://www.jsonschema2pojo.org/
 * Note: In order to use the Parceler library the POJO's member variables cannot be "private"
 */
@Parcel
public class RecipeStep {

  /**
   * The Recipe step used as a Fragment argument
   */
  public static final String ARGUMENT_SELECTED_RECIPE_STEP = "ARGUMENT_SELECTED_RECIPE_STEP";

  @SerializedName("id")
  /* package */ int id;
  @SerializedName("shortDescription")
  /* package */ String shortDescription;
  @SerializedName("description")
  /* package */ String description;
  @SerializedName("videoURL")
  /* package */ String videoUrl; // URL stored as a String
  @SerializedName("thumbnailURL")
  /* package */ String thumbnailUrl; // URL stores as a String

  public RecipeStep() {
    // Mandatory default constructor needed by the Parceler library
  }

  /**
   *
   * @param id
   * @param shortDescription
   * @param description
   * @param videoUrl
   * @param thumbnailUrl
   */
  public RecipeStep(final int id,
      final String shortDescription,
      final String description,
      final String videoUrl,
      final String thumbnailUrl) {
    this.id = id;
    this.shortDescription = shortDescription;
    this.description = description;
    this.videoUrl = videoUrl;
    this.thumbnailUrl = thumbnailUrl;
  }

  public int getId() {
    return id;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public String getDescription() {
    return description;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public String getThumbnailUrl() {
    return thumbnailUrl;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("shortDescription", shortDescription)
        .append("description", description)
        .append("videoUrl", videoUrl)
        .append("thumbnailUrl", thumbnailUrl)
        .toString();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(id)
        .append(shortDescription)
        .append(description)
        .append(videoUrl)
        .append(thumbnailUrl).toHashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof RecipeStep)) {
      return false;
    }
    final RecipeStep rhs = ((RecipeStep) other);
    return new EqualsBuilder()
        .append(id, rhs.id)
        .append(shortDescription, rhs.shortDescription)
        .append(description, rhs.description)
        .append(videoUrl, rhs.videoUrl)
        .append(thumbnailUrl, rhs.thumbnailUrl)
        .isEquals();
  }
}
