package restapi.tqs.Models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "clientId")
@Entity
@Table(name = "`client`")
public class Client {
    
    @Id
    @Column(name = "client_id")
    private long clientId;

    @OneToOne
    @JoinColumn(referencedColumnName = "user_id")
    @MapsId
    private User user;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    public Client() {
    }

    public long getClientId() {
        return this.clientId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }


    @Override
    public String toString() {
        return "{" +
            " clientId='" + getClientId() + "'" +
            ", user='" + getUser() + "'" +
            ", address='" + getAddress() + "'" +
            "}";
    }

}
