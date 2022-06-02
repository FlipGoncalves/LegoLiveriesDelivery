package restapi.tqs.Models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class FavoritesId implements Serializable {
    
    private long clientId;
    private long legoId;

    public FavoritesId() {
    }

    public FavoritesId(long clientId, long legoId) {
        this.clientId = clientId;
        this.legoId = legoId;
    }

    public long getClientId() {
        return this.clientId;
    }

    public long getLegoId() {
        return this.legoId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof FavoritesId)) {
            return false;
        }
        FavoritesId favoritesId = (FavoritesId) o;
        return clientId == favoritesId.clientId && legoId == favoritesId.legoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, legoId);
    }

    @Override
    public String toString() {
        return "{" +
            " clientId='" + getClientId() + "'" +
            ", legoId='" + getLegoId() + "'" +
            "}";
    }

}
