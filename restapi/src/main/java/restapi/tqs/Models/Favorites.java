package restapi.tqs.Models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "favorites")
public class Favorites {
    
    @EmbeddedId
    @JsonIgnore
    FavoritesId id;

    @ManyToOne
    @MapsId("clientId")
    @JsonIdentityReference(alwaysAsId = true)
    private Client client;

    @ManyToOne
    @MapsId("legoId")
    @JsonIdentityReference(alwaysAsId = true)
    private Lego lego;


    public Favorites() {
    }

    public FavoritesId getId() {
        return this.id;
    }

    public void setId(FavoritesId id) {
        this.id = id;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Lego getLego() {
        return this.lego;
    }

    public void setLego(Lego lego) {
        this.lego = lego;
    }

}
