package tqs.project.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "store")
public class Store {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private long storeId;
    @Column(name = "name")
    private String name;
    @Column(name = "latitude", precision = 8, scale = 6)
    private BigDecimal latitude;
    @Column(name = "longitude", precision = 9, scale = 6)
    private BigDecimal longitude;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    @OneToMany(mappedBy = "store")
    private Set<Order> orders = new HashSet<>();

    public Store(){

    }

    public Store(long storeId, String name, BigDecimal latitude, BigDecimal longitude) {
        this.storeId = storeId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public long getStoreId() {
        return this.storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLatitude() {
        return this.latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return this.longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }



    
}
