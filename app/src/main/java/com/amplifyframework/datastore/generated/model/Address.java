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

/** This is an auto generated class representing the Address type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Addresses")
public final class Address implements Model {
  public static final QueryField ID = field("Address", "id");
  public static final QueryField COUNTRY = field("Address", "country");
  public static final QueryField CITY = field("Address", "city");
  public static final QueryField STREET = field("Address", "street");
  public static final QueryField POSTAL_CODE = field("Address", "postalCode");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String country;
  private final @ModelField(targetType="String", isRequired = true) String city;
  private final @ModelField(targetType="String", isRequired = true) String street;
  private final @ModelField(targetType="Int") Integer postalCode;
  private final @ModelField(targetType="Mother") @HasMany(associatedWith = "addressMothersId", type = Mother.class) List<Mother> mothers = null;
  public String getId() {
      return id;
  }
  
  public String getCountry() {
      return country;
  }
  
  public String getCity() {
      return city;
  }
  
  public String getStreet() {
      return street;
  }
  
  public Integer getPostalCode() {
      return postalCode;
  }
  
  public List<Mother> getMothers() {
      return mothers;
  }
  
  private Address(String id, String country, String city, String street, Integer postalCode) {
    this.id = id;
    this.country = country;
    this.city = city;
    this.street = street;
    this.postalCode = postalCode;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Address address = (Address) obj;
      return ObjectsCompat.equals(getId(), address.getId()) &&
              ObjectsCompat.equals(getCountry(), address.getCountry()) &&
              ObjectsCompat.equals(getCity(), address.getCity()) &&
              ObjectsCompat.equals(getStreet(), address.getStreet()) &&
              ObjectsCompat.equals(getPostalCode(), address.getPostalCode());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCountry())
      .append(getCity())
      .append(getStreet())
      .append(getPostalCode())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Address {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("country=" + String.valueOf(getCountry()) + ", ")
      .append("city=" + String.valueOf(getCity()) + ", ")
      .append("street=" + String.valueOf(getStreet()) + ", ")
      .append("postalCode=" + String.valueOf(getPostalCode()))
      .append("}")
      .toString();
  }
  
  public static CountryStep builder() {
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
  public static Address justId(String id) {
    return new Address(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      country,
      city,
      street,
      postalCode);
  }
  public interface CountryStep {
    CityStep country(String country);
  }
  

  public interface CityStep {
    StreetStep city(String city);
  }
  

  public interface StreetStep {
    BuildStep street(String street);
  }
  

  public interface BuildStep {
    Address build();
    BuildStep id(String id);
    BuildStep postalCode(Integer postalCode);
  }
  

  public static class Builder implements CountryStep, CityStep, StreetStep, BuildStep {
    private String id;
    private String country;
    private String city;
    private String street;
    private Integer postalCode;
    @Override
     public Address build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Address(
          id,
          country,
          city,
          street,
          postalCode);
    }
    
    @Override
     public CityStep country(String country) {
        Objects.requireNonNull(country);
        this.country = country;
        return this;
    }
    
    @Override
     public StreetStep city(String city) {
        Objects.requireNonNull(city);
        this.city = city;
        return this;
    }
    
    @Override
     public BuildStep street(String street) {
        Objects.requireNonNull(street);
        this.street = street;
        return this;
    }
    
    @Override
     public BuildStep postalCode(Integer postalCode) {
        this.postalCode = postalCode;
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
    private CopyOfBuilder(String id, String country, String city, String street, Integer postalCode) {
      super.id(id);
      super.country(country)
        .city(city)
        .street(street)
        .postalCode(postalCode);
    }
    
    @Override
     public CopyOfBuilder country(String country) {
      return (CopyOfBuilder) super.country(country);
    }
    
    @Override
     public CopyOfBuilder city(String city) {
      return (CopyOfBuilder) super.city(city);
    }
    
    @Override
     public CopyOfBuilder street(String street) {
      return (CopyOfBuilder) super.street(street);
    }
    
    @Override
     public CopyOfBuilder postalCode(Integer postalCode) {
      return (CopyOfBuilder) super.postalCode(postalCode);
    }
  }
  
}
