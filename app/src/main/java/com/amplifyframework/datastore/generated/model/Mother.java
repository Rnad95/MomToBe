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

/** This is an auto generated class representing the Mother type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Mothers")
public final class Mother implements Model {
  public static final QueryField ID = field("Mother", "id");
  public static final QueryField NAME = field("Mother", "name");
  public static final QueryField IMAGE = field("Mother", "image");
  public static final QueryField NUM_OF_CHILDREN = field("Mother", "numOfChildren");
  public static final QueryField PHONE_NUMBER = field("Mother", "phoneNumber");
  public static final QueryField EMAIL_ADDRESS = field("Mother", "emailAddress");
  public static final QueryField ADDRESS_MOTHERS_ID = field("Mother", "addressMothersId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String image;
  private final @ModelField(targetType="Int", isRequired = true) Integer numOfChildren;
  private final @ModelField(targetType="String") String phoneNumber;
  private final @ModelField(targetType="String", isRequired = true) String emailAddress;
  private final @ModelField(targetType="Comment") @HasMany(associatedWith = "motherCommentsId", type = Comment.class) List<Comment> comments = null;
  private final @ModelField(targetType="Product") @HasMany(associatedWith = "motherProductsId", type = Product.class) List<Product> products = null;
  private final @ModelField(targetType="Question") @HasMany(associatedWith = "motherQuestionsId", type = Question.class) List<Question> questions = null;
  private final @ModelField(targetType="Experience") @HasMany(associatedWith = "motherExperiencesId", type = Experience.class) List<Experience> experiences = null;
  private final @ModelField(targetType="UserBlogs") @HasMany(associatedWith = "mother", type = UserBlogs.class) List<UserBlogs> blogs = null;
  private final @ModelField(targetType="ID") String addressMothersId;
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
  
  public String getEmailAddress() {
      return emailAddress;
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
  
  public String getAddressMothersId() {
      return addressMothersId;
  }
  
  private Mother(String id, String name, String image, Integer numOfChildren, String phoneNumber, String emailAddress, String addressMothersId) {
    this.id = id;
    this.name = name;
    this.image = image;
    this.numOfChildren = numOfChildren;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
    this.addressMothersId = addressMothersId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Mother mother = (Mother) obj;
      return ObjectsCompat.equals(getId(), mother.getId()) &&
              ObjectsCompat.equals(getName(), mother.getName()) &&
              ObjectsCompat.equals(getImage(), mother.getImage()) &&
              ObjectsCompat.equals(getNumOfChildren(), mother.getNumOfChildren()) &&
              ObjectsCompat.equals(getPhoneNumber(), mother.getPhoneNumber()) &&
              ObjectsCompat.equals(getEmailAddress(), mother.getEmailAddress()) &&
              ObjectsCompat.equals(getAddressMothersId(), mother.getAddressMothersId());
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
      .append(getEmailAddress())
      .append(getAddressMothersId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Mother {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("image=" + String.valueOf(getImage()) + ", ")
      .append("numOfChildren=" + String.valueOf(getNumOfChildren()) + ", ")
      .append("phoneNumber=" + String.valueOf(getPhoneNumber()) + ", ")
      .append("emailAddress=" + String.valueOf(getEmailAddress()) + ", ")
      .append("addressMothersId=" + String.valueOf(getAddressMothersId()))
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
  public static Mother justId(String id) {
    return new Mother(
      id,
      null,
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
      emailAddress,
      addressMothersId);
  }
  public interface NameStep {
    NumOfChildrenStep name(String name);
  }
  

  public interface NumOfChildrenStep {
    EmailAddressStep numOfChildren(Integer numOfChildren);
  }
  

  public interface EmailAddressStep {
    BuildStep emailAddress(String emailAddress);
  }
  

  public interface BuildStep {
    Mother build();
    BuildStep id(String id);
    BuildStep image(String image);
    BuildStep phoneNumber(String phoneNumber);
    BuildStep addressMothersId(String addressMothersId);
  }
  

  public static class Builder implements NameStep, NumOfChildrenStep, EmailAddressStep, BuildStep {
    private String id;
    private String name;
    private Integer numOfChildren;
    private String emailAddress;
    private String image;
    private String phoneNumber;
    private String addressMothersId;
    @Override
     public Mother build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Mother(
          id,
          name,
          image,
          numOfChildren,
          phoneNumber,
          emailAddress,
          addressMothersId);
    }
    
    @Override
     public NumOfChildrenStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public EmailAddressStep numOfChildren(Integer numOfChildren) {
        Objects.requireNonNull(numOfChildren);
        this.numOfChildren = numOfChildren;
        return this;
    }
    
    @Override
     public BuildStep emailAddress(String emailAddress) {
        Objects.requireNonNull(emailAddress);
        this.emailAddress = emailAddress;
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
     public BuildStep addressMothersId(String addressMothersId) {
        this.addressMothersId = addressMothersId;
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
    private CopyOfBuilder(String id, String name, String image, Integer numOfChildren, String phoneNumber, String emailAddress, String addressMothersId) {
      super.id(id);
      super.name(name)
        .numOfChildren(numOfChildren)
        .emailAddress(emailAddress)
        .image(image)
        .phoneNumber(phoneNumber)
        .addressMothersId(addressMothersId);
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
     public CopyOfBuilder emailAddress(String emailAddress) {
      return (CopyOfBuilder) super.emailAddress(emailAddress);
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
     public CopyOfBuilder addressMothersId(String addressMothersId) {
      return (CopyOfBuilder) super.addressMothersId(addressMothersId);
    }
  }
  
}
