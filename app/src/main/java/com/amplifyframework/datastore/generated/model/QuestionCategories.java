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

/** This is an auto generated class representing the QuestionCategories type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "QuestionCategories")
@Index(name = "byQuestion", fields = {"questionID"})
@Index(name = "byCategory", fields = {"categoryID"})
public final class QuestionCategories implements Model {
  public static final QueryField ID = field("QuestionCategories", "id");
  public static final QueryField QUESTION = field("QuestionCategories", "questionID");
  public static final QueryField CATEGORY = field("QuestionCategories", "categoryID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Question", isRequired = true) @BelongsTo(targetName = "questionID", type = Question.class) Question question;
  private final @ModelField(targetType="Category", isRequired = true) @BelongsTo(targetName = "categoryID", type = Category.class) Category category;
  public String getId() {
      return id;
  }
  
  public Question getQuestion() {
      return question;
  }
  
  public Category getCategory() {
      return category;
  }
  
  private QuestionCategories(String id, Question question, Category category) {
    this.id = id;
    this.question = question;
    this.category = category;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      QuestionCategories questionCategories = (QuestionCategories) obj;
      return ObjectsCompat.equals(getId(), questionCategories.getId()) &&
              ObjectsCompat.equals(getQuestion(), questionCategories.getQuestion()) &&
              ObjectsCompat.equals(getCategory(), questionCategories.getCategory());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getQuestion())
      .append(getCategory())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("QuestionCategories {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("question=" + String.valueOf(getQuestion()) + ", ")
      .append("category=" + String.valueOf(getCategory()))
      .append("}")
      .toString();
  }
  
  public static QuestionStep builder() {
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
  public static QuestionCategories justId(String id) {
    return new QuestionCategories(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      question,
      category);
  }
  public interface QuestionStep {
    CategoryStep question(Question question);
  }
  

  public interface CategoryStep {
    BuildStep category(Category category);
  }
  

  public interface BuildStep {
    QuestionCategories build();
    BuildStep id(String id);
  }
  

  public static class Builder implements QuestionStep, CategoryStep, BuildStep {
    private String id;
    private Question question;
    private Category category;
    @Override
     public QuestionCategories build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new QuestionCategories(
          id,
          question,
          category);
    }
    
    @Override
     public CategoryStep question(Question question) {
        Objects.requireNonNull(question);
        this.question = question;
        return this;
    }
    
    @Override
     public BuildStep category(Category category) {
        Objects.requireNonNull(category);
        this.category = category;
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
    private CopyOfBuilder(String id, Question question, Category category) {
      super.id(id);
      super.question(question)
        .category(category);
    }
    
    @Override
     public CopyOfBuilder question(Question question) {
      return (CopyOfBuilder) super.question(question);
    }
    
    @Override
     public CopyOfBuilder category(Category category) {
      return (CopyOfBuilder) super.category(category);
    }
  }
  
}
