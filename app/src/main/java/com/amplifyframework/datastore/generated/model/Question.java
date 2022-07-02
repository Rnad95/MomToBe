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

/** This is an auto generated class representing the Question type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Questions")
public final class Question implements Model {
  public static final QueryField ID = field("Question", "id");
  public static final QueryField TITLE = field("Question", "title");
  public static final QueryField DESCRIPTION = field("Question", "description");
  public static final QueryField IMAGE = field("Question", "image");
  public static final QueryField FEATURED = field("Question", "featured");
  public static final QueryField MOTHER_QUESTIONS_ID = field("Question", "motherQuestionsId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String", isRequired = true) String description;
  private final @ModelField(targetType="String") String image;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean featured;
  private final @ModelField(targetType="Comment") @HasMany(associatedWith = "questionCommentsId", type = Comment.class) List<Comment> comments = null;
  private final @ModelField(targetType="QuestionCategories") @HasMany(associatedWith = "question", type = QuestionCategories.class) List<QuestionCategories> categories = null;
  private final @ModelField(targetType="ID") String motherQuestionsId;
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
  
  public List<QuestionCategories> getCategories() {
      return categories;
  }
  
  public String getMotherQuestionsId() {
      return motherQuestionsId;
  }
  
  private Question(String id, String title, String description, String image, Boolean featured, String motherQuestionsId) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.image = image;
    this.featured = featured;
    this.motherQuestionsId = motherQuestionsId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Question question = (Question) obj;
      return ObjectsCompat.equals(getId(), question.getId()) &&
              ObjectsCompat.equals(getTitle(), question.getTitle()) &&
              ObjectsCompat.equals(getDescription(), question.getDescription()) &&
              ObjectsCompat.equals(getImage(), question.getImage()) &&
              ObjectsCompat.equals(getFeatured(), question.getFeatured()) &&
              ObjectsCompat.equals(getMotherQuestionsId(), question.getMotherQuestionsId());
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
      .append(getMotherQuestionsId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Question {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("image=" + String.valueOf(getImage()) + ", ")
      .append("featured=" + String.valueOf(getFeatured()) + ", ")
      .append("motherQuestionsId=" + String.valueOf(getMotherQuestionsId()))
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
  public static Question justId(String id) {
    return new Question(
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
      motherQuestionsId);
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
    Question build();
    BuildStep id(String id);
    BuildStep image(String image);
    BuildStep motherQuestionsId(String motherQuestionsId);
  }
  

  public static class Builder implements TitleStep, DescriptionStep, FeaturedStep, BuildStep {
    private String id;
    private String title;
    private String description;
    private Boolean featured;
    private String image;
    private String motherQuestionsId;
    @Override
     public Question build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Question(
          id,
          title,
          description,
          image,
          featured,
          motherQuestionsId);
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
     public BuildStep motherQuestionsId(String motherQuestionsId) {
        this.motherQuestionsId = motherQuestionsId;
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
    private CopyOfBuilder(String id, String title, String description, String image, Boolean featured, String motherQuestionsId) {
      super.id(id);
      super.title(title)
        .description(description)
        .featured(featured)
        .image(image)
        .motherQuestionsId(motherQuestionsId);
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
     public CopyOfBuilder motherQuestionsId(String motherQuestionsId) {
      return (CopyOfBuilder) super.motherQuestionsId(motherQuestionsId);
    }
  }
  
}
