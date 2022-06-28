package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;

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

/** This is an auto generated class representing the Cat type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Cats")
public final class Cat implements Model {
  public static final QueryField ID = field("Cat", "id");
  public static final QueryField TITLE = field("Cat", "title");
  public static final QueryField DESCRIPTION = field("Cat", "description");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String", isRequired = true) String description;
  private final @ModelField(targetType="ExperienceCategories") @HasMany(associatedWith = "cat", type = ExperienceCategories.class) List<ExperienceCategories> experiences = null;
  private final @ModelField(targetType="BlogCategories") @HasMany(associatedWith = "cat", type = BlogCategories.class) List<BlogCategories> blogs = null;
  private final @ModelField(targetType="QuestionCategories") @HasMany(associatedWith = "cat", type = QuestionCategories.class) List<QuestionCategories> questions = null;
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getDescription() {
      return description;
  }
  
  public List<ExperienceCategories> getExperiences() {
      return experiences;
  }
  
  public List<BlogCategories> getBlogs() {
      return blogs;
  }
  
  public List<QuestionCategories> getQuestions() {
      return questions;
  }
  
  private Cat(String id, String title, String description) {
    this.id = id;
    this.title = title;
    this.description = description;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Cat cat = (Cat) obj;
      return ObjectsCompat.equals(getId(), cat.getId()) &&
              ObjectsCompat.equals(getTitle(), cat.getTitle()) &&
              ObjectsCompat.equals(getDescription(), cat.getDescription());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getDescription())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Cat {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("description=" + String.valueOf(getDescription()))
      .append("}")
      .toString();
  }
  
  public static TitleStep builder() {
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
  public static Cat justId(String id) {
    return new Cat(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      description);
  }
  public interface TitleStep {
    DescriptionStep title(String title);
  }
  

  public interface DescriptionStep {
    BuildStep description(String description);
  }
  

  public interface BuildStep {
    Cat build();
    BuildStep id(String id);
  }
  

  public static class Builder implements TitleStep, DescriptionStep, BuildStep {
    private String id;
    private String title;
    private String description;
    @Override
     public Cat build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Cat(
          id,
          title,
          description);
    }
    
    @Override
     public DescriptionStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        Objects.requireNonNull(description);
        this.description = description;
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
    private CopyOfBuilder(String id, String title, String description) {
      super.id(id);
      super.title(title)
        .description(description);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
  }
  
}
