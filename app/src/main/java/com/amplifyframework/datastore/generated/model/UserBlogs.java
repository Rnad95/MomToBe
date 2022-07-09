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

/** This is an auto generated class representing the UserBlogs type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "UserBlogs")
@Index(name = "byMother", fields = {"motherID"})
@Index(name = "byBlog", fields = {"blogID"})
public final class UserBlogs implements Model {
  public static final QueryField ID = field("UserBlogs", "id");
  public static final QueryField MOTHER = field("UserBlogs", "motherID");
  public static final QueryField BLOG = field("UserBlogs", "blogID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Mother", isRequired = true) @BelongsTo(targetName = "motherID", type = Mother.class) Mother mother;
  private final @ModelField(targetType="Blog", isRequired = true) @BelongsTo(targetName = "blogID", type = Blog.class) Blog blog;
  public String getId() {
      return id;
  }
  
  public Mother getMother() {
      return mother;
  }
  
  public Blog getBlog() {
      return blog;
  }
  
  private UserBlogs(String id, Mother mother, Blog blog) {
    this.id = id;
    this.mother = mother;
    this.blog = blog;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      UserBlogs userBlogs = (UserBlogs) obj;
      return ObjectsCompat.equals(getId(), userBlogs.getId()) &&
              ObjectsCompat.equals(getMother(), userBlogs.getMother()) &&
              ObjectsCompat.equals(getBlog(), userBlogs.getBlog());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getMother())
      .append(getBlog())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserBlogs {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("mother=" + String.valueOf(getMother()) + ", ")
      .append("blog=" + String.valueOf(getBlog()))
      .append("}")
      .toString();
  }
  
  public static MotherStep builder() {
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
  public static UserBlogs justId(String id) {
    return new UserBlogs(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      mother,
      blog);
  }
  public interface MotherStep {
    BlogStep mother(Mother mother);
  }
  

  public interface BlogStep {
    BuildStep blog(Blog blog);
  }
  

  public interface BuildStep {
    UserBlogs build();
    BuildStep id(String id);
  }
  

  public static class Builder implements MotherStep, BlogStep, BuildStep {
    private String id;
    private Mother mother;
    private Blog blog;
    @Override
     public UserBlogs build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserBlogs(
          id,
          mother,
          blog);
    }
    
    @Override
     public BlogStep mother(Mother mother) {
        Objects.requireNonNull(mother);
        this.mother = mother;
        return this;
    }
    
    @Override
     public BuildStep blog(Blog blog) {
        Objects.requireNonNull(blog);
        this.blog = blog;
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
    private CopyOfBuilder(String id, Mother mother, Blog blog) {
      super.id(id);
      super.mother(mother)
        .blog(blog);
    }
    
    @Override
     public CopyOfBuilder mother(Mother mother) {
      return (CopyOfBuilder) super.mother(mother);
    }
    
    @Override
     public CopyOfBuilder blog(Blog blog) {
      return (CopyOfBuilder) super.blog(blog);
    }
  }
  
}
