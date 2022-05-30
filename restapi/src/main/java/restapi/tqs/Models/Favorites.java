package restapi.tqs.Models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "favorites")
public class Favorites {
    
    @EmbeddedId
    FavoritesId id;

    @ManyToOne
    @MapsId("clientId")
    //@JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @ManyToOne
    @MapsId("legoId")
    //@JoinColumn(name = "lego_id",referencedColumnName = "lego_id")
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
