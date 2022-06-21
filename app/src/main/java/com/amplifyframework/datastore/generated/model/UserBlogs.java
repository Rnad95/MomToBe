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
@Index(name = "byUser", fields = {"userID"})
@Index(name = "byBlog", fields = {"blogID"})
public final class UserBlogs implements Model {
  public static final QueryField ID = field("UserBlogs", "id");
  public static final QueryField USER = field("UserBlogs", "userID");
  public static final QueryField BLOG = field("UserBlogs", "blogID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="User", isRequired = true) @BelongsTo(targetName = "userID", type = User.class) User user;
  private final @ModelField(targetType="Blog", isRequired = true) @BelongsTo(targetName = "blogID", type = Blog.class) Blog blog;
  public String getId() {
      return id;
  }
  
  public User getUser() {
      return user;
  }
  
  public Blog getBlog() {
      return blog;
  }
  
  private UserBlogs(String id, User user, Blog blog) {
    this.id = id;
    this.user = user;
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
              ObjectsCompat.equals(getUser(), userBlogs.getUser()) &&
              ObjectsCompat.equals(getBlog(), userBlogs.getBlog());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUser())
      .append(getBlog())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("UserBlogs {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("blog=" + String.valueOf(getBlog()))
      .append("}")
      .toString();
  }
  
  public static UserStep builder() {
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
      user,
      blog);
  }
  public interface UserStep {
    BlogStep user(User user);
  }
  

  public interface BlogStep {
    BuildStep blog(Blog blog);
  }
  

  public interface BuildStep {
    UserBlogs build();
    BuildStep id(String id);
  }
  

  public static class Builder implements UserStep, BlogStep, BuildStep {
    private String id;
    private User user;
    private Blog blog;
    @Override
     public UserBlogs build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new UserBlogs(
          id,
          user,
          blog);
    }
    
    @Override
     public BlogStep user(User user) {
        Objects.requireNonNull(user);
        this.user = user;
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
    private CopyOfBuilder(String id, User user, Blog blog) {
      super.id(id);
      super.user(user)
        .blog(blog);
    }
    
    @Override
     public CopyOfBuilder user(User user) {
      return (CopyOfBuilder) super.user(user);
    }
    
    @Override
     public CopyOfBuilder blog(Blog blog) {
      return (CopyOfBuilder) super.blog(blog);
    }
  }
  
}
