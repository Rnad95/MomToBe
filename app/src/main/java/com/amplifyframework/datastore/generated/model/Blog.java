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

/** This is an auto generated class representing the Blog type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Blogs")
public final class Blog implements Model {
  public static final QueryField ID = field("Blog", "id");
  public static final QueryField TITLE = field("Blog", "title");
  public static final QueryField DESCRIPTION = field("Blog", "description");
  public static final QueryField IMAGE = field("Blog", "image");
  public static final QueryField AUTHER_NAME = field("Blog", "autherName");
  public static final QueryField FEATURED = field("Blog", "featured");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String", isRequired = true) String description;
  private final @ModelField(targetType="String") String image;
  private final @ModelField(targetType="String", isRequired = true) String autherName;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean featured;
  private final @ModelField(targetType="BlogCategories") @HasMany(associatedWith = "blog", type = BlogCategories.class) List<BlogCategories> categories = null;
  private final @ModelField(targetType="UserBlogs") @HasMany(associatedWith = "blog", type = UserBlogs.class) List<UserBlogs> mothers = null;
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
  
  public String getAutherName() {
      return autherName;
  }
  
  public Boolean getFeatured() {
      return featured;
  }
  
  public List<BlogCategories> getCategories() {
      return categories;
  }
  
  public List<UserBlogs> getMothers() {
      return mothers;
  }
  
  private Blog(String id, String title, String description, String image, String autherName, Boolean featured) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.image = image;
    this.autherName = autherName;
    this.featured = featured;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Blog blog = (Blog) obj;
      return ObjectsCompat.equals(getId(), blog.getId()) &&
              ObjectsCompat.equals(getTitle(), blog.getTitle()) &&
              ObjectsCompat.equals(getDescription(), blog.getDescription()) &&
              ObjectsCompat.equals(getImage(), blog.getImage()) &&
              ObjectsCompat.equals(getAutherName(), blog.getAutherName()) &&
              ObjectsCompat.equals(getFeatured(), blog.getFeatured());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getDescription())
      .append(getImage())
      .append(getAutherName())
      .append(getFeatured())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Blog {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("image=" + String.valueOf(getImage()) + ", ")
      .append("autherName=" + String.valueOf(getAutherName()) + ", ")
      .append("featured=" + String.valueOf(getFeatured()))
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
  public static Blog justId(String id) {
    return new Blog(
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
      autherName,
      featured);
  }
  public interface TitleStep {
    DescriptionStep title(String title);
  }
  

  public interface DescriptionStep {
    AutherNameStep description(String description);
  }
  

  public interface AutherNameStep {
    FeaturedStep autherName(String autherName);
  }
  

  public interface FeaturedStep {
    BuildStep featured(Boolean featured);
  }
  

  public interface BuildStep {
    Blog build();
    BuildStep id(String id);
    BuildStep image(String image);
  }
  

  public static class Builder implements TitleStep, DescriptionStep, AutherNameStep, FeaturedStep, BuildStep {
    private String id;
    private String title;
    private String description;
    private String autherName;
    private Boolean featured;
    private String image;
    @Override
     public Blog build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Blog(
          id,
          title,
          description,
          image,
          autherName,
          featured);
    }
    
    @Override
     public DescriptionStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public AutherNameStep description(String description) {
        Objects.requireNonNull(description);
        this.description = description;
        return this;
    }
    
    @Override
     public FeaturedStep autherName(String autherName) {
        Objects.requireNonNull(autherName);
        this.autherName = autherName;
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
    private CopyOfBuilder(String id, String title, String description, String image, String autherName, Boolean featured) {
      super.id(id);
      super.title(title)
        .description(description)
        .autherName(autherName)
        .featured(featured)
        .image(image);
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
     public CopyOfBuilder autherName(String autherName) {
      return (CopyOfBuilder) super.autherName(autherName);
    }
    
    @Override
     public CopyOfBuilder featured(Boolean featured) {
      return (CopyOfBuilder) super.featured(featured);
    }
    
    @Override
     public CopyOfBuilder image(String image) {
      return (CopyOfBuilder) super.image(image);
    }
  }
  
}
