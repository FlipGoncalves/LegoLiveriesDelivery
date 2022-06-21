package tqs.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "manager")
public class Manager {
    
    @Id
    @Column(name = "manager_id")
    private long managerId;

    @OneToOne
    @JoinColumn(referencedColumnName = "user_id")
    @MapsId
    private User user;

    public Manager() {
    }

    public long getManagerId() {
        return this.managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }    
}
