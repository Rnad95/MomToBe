package com.amplifyframework.datastore.generated.model;


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

/** This is an auto generated class representing the Comment type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Comments")
public final class Comment implements Model {
  public static final QueryField ID = field("Comment", "id");
  public static final QueryField CONTENT = field("Comment", "content");
  public static final QueryField USER_COMMENTS_ID = field("Comment", "userCommentsId");
  public static final QueryField PRODUCT_COMMENTS_ID = field("Comment", "productCommentsId");
  public static final QueryField QUESTION_COMMENTS_ID = field("Comment", "questionCommentsId");
  public static final QueryField EXPERIENCE_COMMENTS_ID = field("Comment", "experienceCommentsId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String content;
  private final @ModelField(targetType="ID") String userCommentsId;
  private final @ModelField(targetType="ID") String productCommentsId;
  private final @ModelField(targetType="ID") String questionCommentsId;
  private final @ModelField(targetType="ID") String experienceCommentsId;
  public String getId() {
      return id;
  }
  
  public String getContent() {
      return content;
  }
  
  public String getUserCommentsId() {
      return userCommentsId;
  }
  
  public String getProductCommentsId() {
      return productCommentsId;
  }
  
  public String getQuestionCommentsId() {
      return questionCommentsId;
  }
  
  public String getExperienceCommentsId() {
      return experienceCommentsId;
  }
  
  private Comment(String id, String content, String userCommentsId, String productCommentsId, String questionCommentsId, String experienceCommentsId) {
    this.id = id;
    this.content = content;
    this.userCommentsId = userCommentsId;
    this.productCommentsId = productCommentsId;
    this.questionCommentsId = questionCommentsId;
    this.experienceCommentsId = experienceCommentsId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Comment comment = (Comment) obj;
      return ObjectsCompat.equals(getId(), comment.getId()) &&
              ObjectsCompat.equals(getContent(), comment.getContent()) &&
              ObjectsCompat.equals(getUserCommentsId(), comment.getUserCommentsId()) &&
              ObjectsCompat.equals(getProductCommentsId(), comment.getProductCommentsId()) &&
              ObjectsCompat.equals(getQuestionCommentsId(), comment.getQuestionCommentsId()) &&
              ObjectsCompat.equals(getExperienceCommentsId(), comment.getExperienceCommentsId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getContent())
      .append(getUserCommentsId())
      .append(getProductCommentsId())
      .append(getQuestionCommentsId())
      .append(getExperienceCommentsId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Comment {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("content=" + String.valueOf(getContent()) + ", ")
      .append("userCommentsId=" + String.valueOf(getUserCommentsId()) + ", ")
      .append("productCommentsId=" + String.valueOf(getProductCommentsId()) + ", ")
      .append("questionCommentsId=" + String.valueOf(getQuestionCommentsId()) + ", ")
      .append("experienceCommentsId=" + String.valueOf(getExperienceCommentsId()))
      .append("}")
      .toString();
  }
  
  public static ContentStep builder() {
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
  public static Comment justId(String id) {
    return new Comment(
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
      content,
      userCommentsId,
      productCommentsId,
      questionCommentsId,
      experienceCommentsId);
  }
  public interface ContentStep {
    BuildStep content(String content);
  }
  

  public interface BuildStep {
    Comment build();
    BuildStep id(String id);
    BuildStep userCommentsId(String userCommentsId);
    BuildStep productCommentsId(String productCommentsId);
    BuildStep questionCommentsId(String questionCommentsId);
    BuildStep experienceCommentsId(String experienceCommentsId);
  }
  

  public static class Builder implements ContentStep, BuildStep {
    private String id;
    private String content;
    private String userCommentsId;
    private String productCommentsId;
    private String questionCommentsId;
    private String experienceCommentsId;
    @Override
     public Comment build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Comment(
          id,
          content,
          userCommentsId,
          productCommentsId,
          questionCommentsId,
          experienceCommentsId);
    }
    
    @Override
     public BuildStep content(String content) {
        Objects.requireNonNull(content);
        this.content = content;
        return this;
    }
    
    @Override
     public BuildStep userCommentsId(String userCommentsId) {
        this.userCommentsId = userCommentsId;
        return this;
    }
    
    @Override
     public BuildStep productCommentsId(String productCommentsId) {
        this.productCommentsId = productCommentsId;
        return this;
    }
    
    @Override
     public BuildStep questionCommentsId(String questionCommentsId) {
        this.questionCommentsId = questionCommentsId;
        return this;
    }
    
    @Override
     public BuildStep experienceCommentsId(String experienceCommentsId) {
        this.experienceCommentsId = experienceCommentsId;
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
    private CopyOfBuilder(String id, String content, String userCommentsId, String productCommentsId, String questionCommentsId, String experienceCommentsId) {
      super.id(id);
      super.content(content)
        .userCommentsId(userCommentsId)
        .productCommentsId(productCommentsId)
        .questionCommentsId(questionCommentsId)
        .experienceCommentsId(experienceCommentsId);
    }
    
    @Override
     public CopyOfBuilder content(String content) {
      return (CopyOfBuilder) super.content(content);
    }
    
    @Override
     public CopyOfBuilder userCommentsId(String userCommentsId) {
      return (CopyOfBuilder) super.userCommentsId(userCommentsId);
    }
    
    @Override
     public CopyOfBuilder productCommentsId(String productCommentsId) {
      return (CopyOfBuilder) super.productCommentsId(productCommentsId);
    }
    
    @Override
     public CopyOfBuilder questionCommentsId(String questionCommentsId) {
      return (CopyOfBuilder) super.questionCommentsId(questionCommentsId);
    }
    
    @Override
     public CopyOfBuilder experienceCommentsId(String experienceCommentsId) {
      return (CopyOfBuilder) super.experienceCommentsId(experienceCommentsId);
    }
  }
  
}
