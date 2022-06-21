package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the ExperienceCategories type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "ExperienceCategories")
@Index(name = "byCategory", fields = {"categoryID"})
@Index(name = "byExperience", fields = {"experienceID"})
public final class ExperienceCategories implements Model {
  public static final QueryField ID = field("ExperienceCategories", "id");
  public static final QueryField CATEGORY = field("ExperienceCategories", "categoryID");
  public static final QueryField EXPERIENCE = field("ExperienceCategories", "experienceID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Category", isRequired = true) @BelongsTo(targetName = "categoryID", type = Category.class) Category category;
  private final @ModelField(targetType="Experience", isRequired = true) @BelongsTo(targetName = "experienceID", type = Experience.class) Experience experience;
  public String getId() {
      return id;
  }
  
  public Category getCategory() {
      return category;
  }
  
  public Experience getExperience() {
      return experience;
  }
  
  private ExperienceCategories(String id, Category category, Experience experience) {
    this.id = id;
    this.category = category;
    this.experience = experience;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      ExperienceCategories experienceCategories = (ExperienceCategories) obj;
      return ObjectsCompat.equals(getId(), experienceCategories.getId()) &&
              ObjectsCompat.equals(getCategory(), experienceCategories.getCategory()) &&
              ObjectsCompat.equals(getExperience(), experienceCategories.getExperience());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCategory())
      .append(getExperience())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("ExperienceCategories {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("category=" + String.valueOf(getCategory()) + ", ")
      .append("experience=" + String.valueOf(getExperience()))
      .append("}")
      .toString();
  }
  
  public static CategoryStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static ExperienceCategories justId(String id) {
    return new ExperienceCategories(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      category,
      experience);
  }
  public interface CategoryStep {
    ExperienceStep category(Category category);
  }
  

  public interface ExperienceStep {
    BuildStep experience(Experience experience);
  }
  

  public interface BuildStep {
    ExperienceCategories build();
    BuildStep id(String id);
  }
  

  public static class Builder implements CategoryStep, ExperienceStep, BuildStep {
    private String id;
    private Category category;
    private Experience experience;
    @Override
     public ExperienceCategories build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new ExperienceCategories(
          id,
          category,
          experience);
    }
    
    @Override
     public ExperienceStep category(Category category) {
        Objects.requireNonNull(category);
        this.category = category;
        return this;
    }
    
    @Override
     public BuildStep experience(Experience experience) {
        Objects.requireNonNull(experience);
        this.experience = experience;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, Category category, Experience experience) {
      super.id(id);
      super.category(category)
        .experience(experience);
    }
    
    @Override
     public CopyOfBuilder category(Category category) {
      return (CopyOfBuilder) super.category(category);
    }
    
    @Override
     public CopyOfBuilder experience(Experience experience) {
      return (CopyOfBuilder) super.experience(experience);
    }
  }
  
}
