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
@Index(name = "byCat", fields = {"catID"})
@Index(name = "byBlog", fields = {"blogID"})
public final class BlogCategories implements Model {
  public static final QueryField ID = field("BlogCategories", "id");
  public static final QueryField CAT = field("BlogCategories", "catID");
  public static final QueryField BLOG = field("BlogCategories", "blogID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Cat", isRequired = true) @BelongsTo(targetName = "catID", type = Cat.class) Cat cat;
  private final @ModelField(targetType="Blog", isRequired = true) @BelongsTo(targetName = "blogID", type = Blog.class) Blog blog;
  public String getId() {
      return id;
  }
  
  public Cat getCat() {
      return cat;
  }
  
  public Blog getBlog() {
      return blog;
  }
  
  private BlogCategories(String id, Cat cat, Blog blog) {
    this.id = id;
    this.cat = cat;
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
              ObjectsCompat.equals(getCat(), blogCategories.getCat()) &&
              ObjectsCompat.equals(getBlog(), blogCategories.getBlog());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCat())
      .append(getBlog())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("BlogCategories {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("cat=" + String.valueOf(getCat()) + ", ")
      .append("blog=" + String.valueOf(getBlog()))
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
  public static BlogCategories justId(String id) {
    return new BlogCategories(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      cat,
      blog);
  }
  public interface CatStep {
    BlogStep cat(Cat cat);
  }
  

  public interface BlogStep {
    BuildStep blog(Blog blog);
  }
  

  public interface BuildStep {
    BlogCategories build();
    BuildStep id(String id);
  }
  

  public static class Builder implements CatStep, BlogStep, BuildStep {
    private String id;
    private Cat cat;
    private Blog blog;
    @Override
     public BlogCategories build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new BlogCategories(
          id,
          cat,
          blog);
    }
    
    @Override
     public BlogStep cat(Cat cat) {
        Objects.requireNonNull(cat);
        this.cat = cat;
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
    private CopyOfBuilder(String id, Cat cat, Blog blog) {
      super.id(id);
      super.cat(cat)
        .blog(blog);
    }
    
    @Override
     public CopyOfBuilder cat(Cat cat) {
      return (CopyOfBuilder) super.cat(cat);
    }
    
    @Override
     public CopyOfBuilder blog(Blog blog) {
      return (CopyOfBuilder) super.blog(blog);
    }
  }
  
}
