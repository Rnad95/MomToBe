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

/** This is an auto generated class representing the User type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users")
public final class User implements Model {
  public static final QueryField ID = field("User", "id");
  public static final QueryField NAME = field("User", "name");
  public static final QueryField IMAGE = field("User", "image");
  public static final QueryField NUM_OF_CHILDREN = field("User", "numOfChildren");
  public static final QueryField PHONE_NUMBER = field("User", "phoneNumber");
  public static final QueryField ADDRESS_USERS_ID = field("User", "addressUsersId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String image;
  private final @ModelField(targetType="Int", isRequired = true) Integer numOfChildren;
  private final @ModelField(targetType="String") String phoneNumber;
  private final @ModelField(targetType="Comment") @HasMany(associatedWith = "userCommentsId", type = Comment.class) List<Comment> comments = null;
  private final @ModelField(targetType="Product") @HasMany(associatedWith = "userProductsId", type = Product.class) List<Product> products = null;
  private final @ModelField(targetType="Question") @HasMany(associatedWith = "userQuestionsId", type = Question.class) List<Question> questions = null;
  private final @ModelField(targetType="Experience") @HasMany(associatedWith = "userExperiencesId", type = Experience.class) List<Experience> experiences = null;
  private final @ModelField(targetType="UserBlogs") @HasMany(associatedWith = "user", type = UserBlogs.class) List<UserBlogs> blogs = null;
  private final @ModelField(targetType="ID") String addressUsersId;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getImage() {
      return image;
  }
  
  public Integer getNumOfChildren() {
      return numOfChildren;
  }
  
  public String getPhoneNumber() {
      return phoneNumber;
  }
  
  public List<Comment> getComments() {
      return comments;
  }
  
  public List<Product> getProducts() {
      return products;
  }
  
  public List<Question> getQuestions() {
      return questions;
  }
  
  public List<Experience> getExperiences() {
      return experiences;
  }
  
  public List<UserBlogs> getBlogs() {
      return blogs;
  }
  
  public String getAddressUsersId() {
      return addressUsersId;
  }
  
  private User(String id, String name, String image, Integer numOfChildren, String phoneNumber, String addressUsersId) {
    this.id = id;
    this.name = name;
    this.image = image;
    this.numOfChildren = numOfChildren;
    this.phoneNumber = phoneNumber;
    this.addressUsersId = addressUsersId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      User user = (User) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getName(), user.getName()) &&
              ObjectsCompat.equals(getImage(), user.getImage()) &&
              ObjectsCompat.equals(getNumOfChildren(), user.getNumOfChildren()) &&
              ObjectsCompat.equals(getPhoneNumber(), user.getPhoneNumber()) &&
              ObjectsCompat.equals(getAddressUsersId(), user.getAddressUsersId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getImage())
      .append(getNumOfChildren())
      .append(getPhoneNumber())
      .append(getAddressUsersId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("User {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("image=" + String.valueOf(getImage()) + ", ")
      .append("numOfChildren=" + String.valueOf(getNumOfChildren()) + ", ")
      .append("phoneNumber=" + String.valueOf(getPhoneNumber()) + ", ")
      .append("addressUsersId=" + String.valueOf(getAddressUsersId()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
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
  public static User justId(String id) {
    return new User(
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
      name,
      image,
      numOfChildren,
      phoneNumber,
      addressUsersId);
  }
  public interface NameStep {
    NumOfChildrenStep name(String name);
  }
  

  public interface NumOfChildrenStep {
    BuildStep numOfChildren(Integer numOfChildren);
  }
  

  public interface BuildStep {
    User build();
    BuildStep id(String id);
    BuildStep image(String image);
    BuildStep phoneNumber(String phoneNumber);
    BuildStep addressUsersId(String addressUsersId);
  }
  

  public static class Builder implements NameStep, NumOfChildrenStep, BuildStep {
    private String id;
    private String name;
    private Integer numOfChildren;
    private String image;
    private String phoneNumber;
    private String addressUsersId;
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          name,
          image,
          numOfChildren,
          phoneNumber,
          addressUsersId);
    }
    
    @Override
     public NumOfChildrenStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep numOfChildren(Integer numOfChildren) {
        Objects.requireNonNull(numOfChildren);
        this.numOfChildren = numOfChildren;
        return this;
    }
    
    @Override
     public BuildStep image(String image) {
        this.image = image;
        return this;
    }
    
    @Override
     public BuildStep phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
    
    @Override
     public BuildStep addressUsersId(String addressUsersId) {
        this.addressUsersId = addressUsersId;
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
    private CopyOfBuilder(String id, String name, String image, Integer numOfChildren, String phoneNumber, String addressUsersId) {
      super.id(id);
      super.name(name)
        .numOfChildren(numOfChildren)
        .image(image)
        .phoneNumber(phoneNumber)
        .addressUsersId(addressUsersId);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder numOfChildren(Integer numOfChildren) {
      return (CopyOfBuilder) super.numOfChildren(numOfChildren);
    }
    
    @Override
     public CopyOfBuilder image(String image) {
      return (CopyOfBuilder) super.image(image);
    }
    
    @Override
     public CopyOfBuilder phoneNumber(String phoneNumber) {
      return (CopyOfBuilder) super.phoneNumber(phoneNumber);
    }
    
    @Override
     public CopyOfBuilder addressUsersId(String addressUsersId) {
      return (CopyOfBuilder) super.addressUsersId(addressUsersId);
    }
  }
  
}
