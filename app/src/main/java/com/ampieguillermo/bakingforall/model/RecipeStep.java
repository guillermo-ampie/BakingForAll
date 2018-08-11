package com.ampieguillermo.bakingforall.model;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * class RecipeStep: Represents a step when preparing a recipe
 * POJO class created with: http://www.jsonschema2pojo.org/
 * Note: In order to use the Parceler library the POJO's member variables cannot be "private"
 */
//@Parcel
public class RecipeStep {

  @SerializedName("id")
  /* package */ final int id;
  @SerializedName("shortDescription")
  /* package */ final String shortDescription;
  @SerializedName("description")
  /* package */ final String description;
  @SerializedName("videoURL")
  /* package */ final String videoURL; // URL stored as a String
  @SerializedName("thumbnailURL")
  /* package */ final String thumbnailURL; // URL stores as a String

  /**
   *
   * @param id
   * @param shortDescription
   * @param description
   * @param videoURL
   * @param thumbnailURL
   */
  public RecipeStep(final int id,
      final String shortDescription,
      final String description,
      final String videoURL,
      final String thumbnailURL) {
    this.id = id;
    this.shortDescription = shortDescription;
    this.description = description;
    this.videoURL = videoURL;
    this.thumbnailURL = thumbnailURL;
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

  public String getVideoURL() {
    return videoURL;
  }

  public String getThumbnailURL() {
    return thumbnailURL;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("shortDescription", shortDescription)
        .append("description", description)
        .append("videoURL", videoURL)
        .append("thumbnailURL", thumbnailURL)
        .toString();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(id)
        .append(shortDescription)
        .append(description)
        .append(videoURL)
        .append(thumbnailURL).toHashCode();
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
        .append(videoURL, rhs.videoURL)
        .append(thumbnailURL, rhs.thumbnailURL)
        .isEquals();
  }
}
