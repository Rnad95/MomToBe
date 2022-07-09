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
@Index(name = "byCat", fields = {"catID"})
@Index(name = "byExperience", fields = {"experienceID"})
public final class ExperienceCategories implements Model {
  public static final QueryField ID = field("ExperienceCategories", "id");
  public static final QueryField CAT = field("ExperienceCategories", "catID");
  public static final QueryField EXPERIENCE = field("ExperienceCategories", "experienceID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Cat", isRequired = true) @BelongsTo(targetName = "catID", type = Cat.class) Cat cat;
  private final @ModelField(targetType="Experience", isRequired = true) @BelongsTo(targetName = "experienceID", type = Experience.class) Experience experience;
  public String getId() {
      return id;
  }
  
  public Cat getCat() {
      return cat;
  }
  
  public Experience getExperience() {
      return experience;
  }
  
  private ExperienceCategories(String id, Cat cat, Experience experience) {
    this.id = id;
    this.cat = cat;
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
              ObjectsCompat.equals(getCat(), experienceCategories.getCat()) &&
              ObjectsCompat.equals(getExperience(), experienceCategories.getExperience());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCat())
      .append(getExperience())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("ExperienceCategories {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("cat=" + String.valueOf(getCat()) + ", ")
      .append("experience=" + String.valueOf(getExperience()))
      .append("}")
      .toString();
  }
  
  public static CatStep builder() {
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
      cat,
      experience);
  }
  public interface CatStep {
    ExperienceStep cat(Cat cat);
  }
  

  public interface ExperienceStep {
    BuildStep experience(Experience experience);
  }
  

  public interface BuildStep {
    ExperienceCategories build();
    BuildStep id(String id);
  }
  

  public static class Builder implements CatStep, ExperienceStep, BuildStep {
    private String id;
    private Cat cat;
    private Experience experience;
    @Override
     public ExperienceCategories build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new ExperienceCategories(
          id,
          cat,
          experience);
    }
    
    @Override
     public ExperienceStep cat(Cat cat) {
        Objects.requireNonNull(cat);
        this.cat = cat;
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
    private CopyOfBuilder(String id, Cat cat, Experience experience) {
      super.id(id);
      super.cat(cat)
        .experience(experience);
    }
    
    @Override
     public CopyOfBuilder cat(Cat cat) {
      return (CopyOfBuilder) super.cat(cat);
    }
    
    @Override
     public CopyOfBuilder experience(Experience experience) {
      return (CopyOfBuilder) super.experience(experience);
    }
  }
  
}
