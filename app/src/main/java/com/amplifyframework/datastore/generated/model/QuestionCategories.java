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
@Index(name = "byCat", fields = {"catID"})
public final class QuestionCategories implements Model {
  public static final QueryField ID = field("QuestionCategories", "id");
  public static final QueryField QUESTION = field("QuestionCategories", "questionID");
  public static final QueryField CAT = field("QuestionCategories", "catID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Question", isRequired = true) @BelongsTo(targetName = "questionID", type = Question.class) Question question;
  private final @ModelField(targetType="Cat", isRequired = true) @BelongsTo(targetName = "catID", type = Cat.class) Cat cat;
  public String getId() {
      return id;
  }
  
  public Question getQuestion() {
      return question;
  }
  
  public Cat getCat() {
      return cat;
  }
  
  private QuestionCategories(String id, Question question, Cat cat) {
    this.id = id;
    this.question = question;
    this.cat = cat;
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
              ObjectsCompat.equals(getCat(), questionCategories.getCat());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getQuestion())
      .append(getCat())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("QuestionCategories {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("question=" + String.valueOf(getQuestion()) + ", ")
      .append("cat=" + String.valueOf(getCat()))
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
      cat);
  }
  public interface QuestionStep {
    CatStep question(Question question);
  }
  

  public interface CatStep {
    BuildStep cat(Cat cat);
  }
  

  public interface BuildStep {
    QuestionCategories build();
    BuildStep id(String id);
  }
  

  public static class Builder implements QuestionStep, CatStep, BuildStep {
    private String id;
    private Question question;
    private Cat cat;
    @Override
     public QuestionCategories build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new QuestionCategories(
          id,
          question,
          cat);
    }
    
    @Override
     public CatStep question(Question question) {
        Objects.requireNonNull(question);
        this.question = question;
        return this;
    }
    
    @Override
     public BuildStep cat(Cat cat) {
        Objects.requireNonNull(cat);
        this.cat = cat;
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
    private CopyOfBuilder(String id, Question question, Cat cat) {
      super.id(id);
      super.question(question)
        .cat(cat);
    }
    
    @Override
     public CopyOfBuilder question(Question question) {
      return (CopyOfBuilder) super.question(question);
    }
    
    @Override
     public CopyOfBuilder cat(Cat cat) {
      return (CopyOfBuilder) super.cat(cat);
    }
  }
  
}
