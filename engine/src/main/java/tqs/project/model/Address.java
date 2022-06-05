package tqs.project.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "address")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "addressId")
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private long addressId;
    @Column(name = "street")
    private String street;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Store store;

    @OneToMany(mappedBy = "address")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Order> orders = new HashSet<>();

    public Address() {
    }

    public Address(long addressId, String street, String postalCode, String city, String country) {
        this.addressId = addressId;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }
    

    public long getAddressId() {
        return this.addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    @Override
    public String toString() {
        return "{" +
            " addressId='" + getAddressId() + "'" +
            ", street='" + getStreet() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
    
}
