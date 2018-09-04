package com.ampieguillermo.bakingforall.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.parceler.Parcel;

/**
 * class Recipe: Represents a Recipe to prepare something delicious
 * POJO class created with: http://www.jsonschema2pojo.org/
 * Note: In order to use the Parceler library the POJO's member variables cannot be "private"
 */
@Parcel
public class Recipe {

  /**
   * The Recipe used as a Fragment argument
   */
  public static final String ARGUMENT_SELECTED_RECIPE = "ARGUMENT_SELECTED_RECIPE";

  @SerializedName("id")
  /* package */ int id;
  @SerializedName("name")
  /* package */ String name;
  @SerializedName("ingredients")
  /* package */ List<Ingredient> ingredients;
  @SerializedName("steps")
  /* package */ List<RecipeStep> steps;
  @SerializedName("servings")
  /* package */ int servings;
  @SerializedName("image")
  /* package */ String image;

  public Recipe() {
    // Mandatory default constructor needed by the Parceler library
  }

  /**
   *
   * @param ingredients
   * @param id
   * @param servings
   * @param name
   * @param image
   * @param steps
   */
  public Recipe(final int id,
      final String name,
      final List<Ingredient> ingredients,
      final List<RecipeStep> steps,
      final int servings,
      final String image) {
    this.id = id;
    this.name = name;
    this.ingredients = new ArrayList<>(ingredients);
    this.steps = new ArrayList<>(steps);
    this.servings = servings;
    this.image = image;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<Ingredient> getIngredients() {
    return Collections.unmodifiableList(ingredients);
  }

  public List<RecipeStep> getSteps() {
    return Collections.unmodifiableList(steps);
  }

  public int getServings() {
    return servings;
  }

  public String getImage() {
    return image;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("name", name)
        .append("ingredients", ingredients)
        .append("steps", steps)
        .append("servings", servings)
        .append("image", image)
        .toString();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(ingredients)
        .append(id)
        .append(servings)
        .append(name)
        .append(image)
        .append(steps)
        .toHashCode();
  }

  @Override
  public boolean equals(final Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof Recipe)) {
      return false;
    }
    final Recipe rhs = ((Recipe) other);
    return new EqualsBuilder()
        .append(ingredients, rhs.ingredients)
        .append(id, rhs.id)
        .append(servings, rhs.servings)
        .append(name, rhs.name)
        .append(image, rhs.image)
        .append(steps, rhs.steps)
        .isEquals();
  }
}
