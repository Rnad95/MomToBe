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

/** This is an auto generated class representing the BlogCategories type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "BlogCategories")
@Index(name = "byCategory", fields = {"categoryID"})
@Index(name = "byBlog", fields = {"blogID"})
public final class BlogCategories implements Model {
  public static final QueryField ID = field("BlogCategories", "id");
  public static final QueryField CATEGORY = field("BlogCategories", "categoryID");
  public static final QueryField BLOG = field("BlogCategories", "blogID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Category", isRequired = true) @BelongsTo(targetName = "categoryID", type = Category.class) Category category;
  private final @ModelField(targetType="Blog", isRequired = true) @BelongsTo(targetName = "blogID", type = Blog.class) Blog blog;
  public String getId() {
      return id;
  }
  
  public Category getCategory() {
      return category;
  }
  
  public Blog getBlog() {
      return blog;
  }
  
  private BlogCategories(String id, Category category, Blog blog) {
    this.id = id;
    this.category = category;
    this.blog = blog;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      BlogCategories blogCategories = (BlogCategories) obj;
      return ObjectsCompat.equals(getId(), blogCategories.getId()) &&
              ObjectsCompat.equals(getCategory(), blogCategories.getCategory()) &&
              ObjectsCompat.equals(getBlog(), blogCategories.getBlog());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCategory())
      .append(getBlog())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("BlogCategories {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("category=" + String.valueOf(getCategory()) + ", ")
      .append("blog=" + String.valueOf(getBlog()))
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
  public static BlogCategories justId(String id) {
    return new BlogCategories(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      category,
      blog);
  }
  public interface CategoryStep {
    BlogStep category(Category category);
  }
  

  public interface BlogStep {
    BuildStep blog(Blog blog);
  }
  

  public interface BuildStep {
    BlogCategories build();
    BuildStep id(String id);
  }
  

  public static class Builder implements CategoryStep, BlogStep, BuildStep {
    private String id;
    private Category category;
    private Blog blog;
    @Override
     public BlogCategories build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new BlogCategories(
          id,
          category,
          blog);
    }
    
    @Override
     public BlogStep category(Category category) {
        Objects.requireNonNull(category);
        this.category = category;
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
    private CopyOfBuilder(String id, Category category, Blog blog) {
      super.id(id);
      super.category(category)
        .blog(blog);
    }
    
    @Override
     public CopyOfBuilder category(Category category) {
      return (CopyOfBuilder) super.category(category);
    }
    
    @Override
     public CopyOfBuilder blog(Blog blog) {
      return (CopyOfBuilder) super.blog(blog);
    }
  }
  
}
