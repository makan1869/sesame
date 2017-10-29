package ir.serenade.sesame.domain.entity;

import ir.serenade.sesame.domain.base.BaseDomain;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class Role extends BaseDomain implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(unique = true)
    String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
