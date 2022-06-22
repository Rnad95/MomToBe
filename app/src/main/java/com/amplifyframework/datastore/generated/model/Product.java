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

/** This is an auto generated class representing the Product type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Products")
public final class Product implements Model {
  public static final QueryField ID = field("Product", "id");
  public static final QueryField TITLE = field("Product", "title");
  public static final QueryField PRICE = field("Product", "price");
  public static final QueryField DESCRIPTION = field("Product", "description");
  public static final QueryField QUANTITY = field("Product", "quantity");
  public static final QueryField IMAGE = field("Product", "image");
  public static final QueryField FEATURED = field("Product", "featured");
  public static final QueryField MOTHER_PRODUCTS_ID = field("Product", "motherProductsId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="Float", isRequired = true) Double price;
  private final @ModelField(targetType="String", isRequired = true) String description;
  private final @ModelField(targetType="Int") Integer quantity;
  private final @ModelField(targetType="String") String image;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean featured;
  private final @ModelField(targetType="Comment") @HasMany(associatedWith = "productCommentsId", type = Comment.class) List<Comment> comments = null;
  private final @ModelField(targetType="ID") String motherProductsId;
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public Double getPrice() {
      return price;
  }
  
  public String getDescription() {
      return description;
  }
  
  public Integer getQuantity() {
      return quantity;
  }
  
  public String getImage() {
      return image;
  }
  
  public Boolean getFeatured() {
      return featured;
  }
  
  public List<Comment> getComments() {
      return comments;
  }
  
  public String getMotherProductsId() {
      return motherProductsId;
  }
  
  private Product(String id, String title, Double price, String description, Integer quantity, String image, Boolean featured, String motherProductsId) {
    this.id = id;
    this.title = title;
    this.price = price;
    this.description = description;
    this.quantity = quantity;
    this.image = image;
    this.featured = featured;
    this.motherProductsId = motherProductsId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Product product = (Product) obj;
      return ObjectsCompat.equals(getId(), product.getId()) &&
              ObjectsCompat.equals(getTitle(), product.getTitle()) &&
              ObjectsCompat.equals(getPrice(), product.getPrice()) &&
              ObjectsCompat.equals(getDescription(), product.getDescription()) &&
              ObjectsCompat.equals(getQuantity(), product.getQuantity()) &&
              ObjectsCompat.equals(getImage(), product.getImage()) &&
              ObjectsCompat.equals(getFeatured(), product.getFeatured()) &&
              ObjectsCompat.equals(getMotherProductsId(), product.getMotherProductsId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getPrice())
      .append(getDescription())
      .append(getQuantity())
      .append(getImage())
      .append(getFeatured())
      .append(getMotherProductsId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Product {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("price=" + String.valueOf(getPrice()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("quantity=" + String.valueOf(getQuantity()) + ", ")
      .append("image=" + String.valueOf(getImage()) + ", ")
      .append("featured=" + String.valueOf(getFeatured()) + ", ")
      .append("motherProductsId=" + String.valueOf(getMotherProductsId()))
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
  public static Product justId(String id) {
    return new Product(
      id,
      null,
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
      title,
      price,
      description,
      quantity,
      image,
      featured,
      motherProductsId);
  }
  public interface TitleStep {
    PriceStep title(String title);
  }
  

  public interface PriceStep {
    DescriptionStep price(Double price);
  }
  

  public interface DescriptionStep {
    FeaturedStep description(String description);
  }
  

  public interface FeaturedStep {
    BuildStep featured(Boolean featured);
  }
  

  public interface BuildStep {
    Product build();
    BuildStep id(String id);
    BuildStep quantity(Integer quantity);
    BuildStep image(String image);
    BuildStep motherProductsId(String motherProductsId);
  }
  

  public static class Builder implements TitleStep, PriceStep, DescriptionStep, FeaturedStep, BuildStep {
    private String id;
    private String title;
    private Double price;
    private String description;
    private Boolean featured;
    private Integer quantity;
    private String image;
    private String motherProductsId;
    @Override
     public Product build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Product(
          id,
          title,
          price,
          description,
          quantity,
          image,
          featured,
          motherProductsId);
    }
    
    @Override
     public PriceStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public DescriptionStep price(Double price) {
        Objects.requireNonNull(price);
        this.price = price;
        return this;
    }
    
    @Override
     public FeaturedStep description(String description) {
        Objects.requireNonNull(description);
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep featured(Boolean featured) {
        Objects.requireNonNull(featured);
        this.featured = featured;
        return this;
    }
    
    @Override
     public BuildStep quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
    
    @Override
     public BuildStep image(String image) {
        this.image = image;
        return this;
    }
    
    @Override
     public BuildStep motherProductsId(String motherProductsId) {
        this.motherProductsId = motherProductsId;
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
    private CopyOfBuilder(String id, String title, Double price, String description, Integer quantity, String image, Boolean featured, String motherProductsId) {
      super.id(id);
      super.title(title)
        .price(price)
        .description(description)
        .featured(featured)
        .quantity(quantity)
        .image(image)
        .motherProductsId(motherProductsId);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder price(Double price) {
      return (CopyOfBuilder) super.price(price);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder featured(Boolean featured) {
      return (CopyOfBuilder) super.featured(featured);
    }
    
    @Override
     public CopyOfBuilder quantity(Integer quantity) {
      return (CopyOfBuilder) super.quantity(quantity);
    }
    
    @Override
     public CopyOfBuilder image(String image) {
      return (CopyOfBuilder) super.image(image);
    }
    
    @Override
     public CopyOfBuilder motherProductsId(String motherProductsId) {
      return (CopyOfBuilder) super.motherProductsId(motherProductsId);
    }
  }
  
}
