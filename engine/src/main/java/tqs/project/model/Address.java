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

import tqs.project.datamodels.AddressDTO;

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
    @Column(name = "latitude", precision = 8, scale = 6)
    private double latitude;
    @Column(name = "longitude", precision = 9, scale = 6)
    private double longitude;
    
    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Store store;

    @OneToMany(mappedBy = "address")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Order> orders = new HashSet<>();

    public Address() {
    }

    public Address(String street, String postalCode, String city, String country, double latitude, double longitude) {
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Store getStore() {
        return this.store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }    

    public void convertDTOtoObject(AddressDTO dto){
        this.setCity(dto.getCity());
        this.setCountry(dto.getCountry());
        this.setPostalCode(dto.getPostalCode());
        this.setStreet(dto.getStreet());
        this.setLongitude(dto.getLongitude());
        this.setLatitude(dto.getLongitude());
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
