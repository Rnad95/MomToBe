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

/** This is an auto generated class representing the Experience type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Experiences")
public final class Experience implements Model {
  public static final QueryField ID = field("Experience", "id");
  public static final QueryField TITLE = field("Experience", "title");
  public static final QueryField DESCRIPTION = field("Experience", "description");
  public static final QueryField IMAGE = field("Experience", "image");
  public static final QueryField FEATURED = field("Experience", "featured");
  public static final QueryField MOTHER_EXPERIENCES_ID = field("Experience", "motherExperiencesId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String", isRequired = true) String description;
  private final @ModelField(targetType="String") String image;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean featured;
  private final @ModelField(targetType="Comment") @HasMany(associatedWith = "experienceCommentsId", type = Comment.class) List<Comment> comments = null;
  private final @ModelField(targetType="ExperienceCategories") @HasMany(associatedWith = "experience", type = ExperienceCategories.class) List<ExperienceCategories> categories = null;
  private final @ModelField(targetType="ID") String motherExperiencesId;
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getImage() {
      return image;
  }
  
  public Boolean getFeatured() {
      return featured;
  }
  
  public List<Comment> getComments() {
      return comments;
  }
  
  public List<ExperienceCategories> getCategories() {
      return categories;
  }
  
  public String getMotherExperiencesId() {
      return motherExperiencesId;
  }
  
  private Experience(String id, String title, String description, String image, Boolean featured, String motherExperiencesId) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.image = image;
    this.featured = featured;
    this.motherExperiencesId = motherExperiencesId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Experience experience = (Experience) obj;
      return ObjectsCompat.equals(getId(), experience.getId()) &&
              ObjectsCompat.equals(getTitle(), experience.getTitle()) &&
              ObjectsCompat.equals(getDescription(), experience.getDescription()) &&
              ObjectsCompat.equals(getImage(), experience.getImage()) &&
              ObjectsCompat.equals(getFeatured(), experience.getFeatured()) &&
              ObjectsCompat.equals(getMotherExperiencesId(), experience.getMotherExperiencesId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getDescription())
      .append(getImage())
      .append(getFeatured())
      .append(getMotherExperiencesId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Experience {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("image=" + String.valueOf(getImage()) + ", ")
      .append("featured=" + String.valueOf(getFeatured()) + ", ")
      .append("motherExperiencesId=" + String.valueOf(getMotherExperiencesId()))
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
  public static Experience justId(String id) {
    return new Experience(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      description,
      image,
      featured,
      motherExperiencesId);
  }
  public interface TitleStep {
    DescriptionStep title(String title);
  }
  

  public interface DescriptionStep {
    FeaturedStep description(String description);
  }
  

  public interface FeaturedStep {
    BuildStep featured(Boolean featured);
  }
  

  public interface BuildStep {
    Experience build();
    BuildStep id(String id);
    BuildStep image(String image);
    BuildStep motherExperiencesId(String motherExperiencesId);
  }
  

  public static class Builder implements TitleStep, DescriptionStep, FeaturedStep, BuildStep {
    private String id;
    private String title;
    private String description;
    private Boolean featured;
    private String image;
    private String motherExperiencesId;
    @Override
     public Experience build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Experience(
          id,
          title,
          description,
          image,
          featured,
          motherExperiencesId);
    }
    
    @Override
     public DescriptionStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public FeaturedStep description(String description) {
        Objects.requireNonNull(description);
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep featured(Boolean featured) {
        Objects.requireNonNull(featured);
        this.featured = featured;
        return this;
    }
    
    @Override
     public BuildStep image(String image) {
        this.image = image;
        return this;
    }
    
    @Override
     public BuildStep motherExperiencesId(String motherExperiencesId) {
        this.motherExperiencesId = motherExperiencesId;
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
    private CopyOfBuilder(String id, String title, String description, String image, Boolean featured, String motherExperiencesId) {
      super.id(id);
      super.title(title)
        .description(description)
        .featured(featured)
        .image(image)
        .motherExperiencesId(motherExperiencesId);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder featured(Boolean featured) {
      return (CopyOfBuilder) super.featured(featured);
    }
    
    @Override
     public CopyOfBuilder image(String image) {
      return (CopyOfBuilder) super.image(image);
    }
    
    @Override
     public CopyOfBuilder motherExperiencesId(String motherExperiencesId) {
      return (CopyOfBuilder) super.motherExperiencesId(motherExperiencesId);
    }
  }
  
}
