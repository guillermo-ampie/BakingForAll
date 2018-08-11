package com.ampieguillermo.bakingforall.model;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class Ingredient: Represents an ingredient used in a Recipe
 * POJO class created with: http://www.jsonschema2pojo.org/
 * Note: In order to use the Parceler library the POJO's member variables cannot be "private"
 */

//@Parcel
public class Ingredient {

  @SerializedName("quantity")
  /* package */ final double quantity;
  @SerializedName("measure")
  /* package */ final String measure;
  @SerializedName("ingredient")
  /* package */ final String ingredient;

  /**
   *
   * @param measure
   * @param ingredient
   * @param quantity
   */
  public Ingredient(final double quantity, final String measure, final String ingredient) {
    this.quantity = quantity;
    this.measure = measure;
    this.ingredient = ingredient;
  }

  public double getQuantity() {
    return quantity;
  }

  public String getMeasure() {
    return measure;
  }

  public String getIngredient() {
    return ingredient;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("quantity", quantity)
        .append("measure", measure)
        .append("ingredient", ingredient)
        .toString();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(measure)
        .append(ingredient)
        .append(quantity)
        .toHashCode();
  }

  @Override
  public boolean equals(final Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof Ingredient)) {
      return false;
    }
    final Ingredient rhs = ((Ingredient) other);
    return new EqualsBuilder()
        .append(measure, rhs.measure)
        .append(ingredient, rhs.ingredient)
        .append(quantity, rhs.quantity)
        .isEquals();
  }
}