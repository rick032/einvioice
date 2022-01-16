package rick.einvioice.model;

import javax.persistence.*;

@Entity
@Table(name = "EinvAuthorities", indexes = {
        @Index(name = "ix_einvauth_username", columnList = "username,authority")
})
public class EinvAuthorities {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private EinvUsers users;

    private String authority;

    public EinvUsers getUsers() {
        return users;
    }

    public void setUsers(EinvUsers users) {
        this.users = users;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
