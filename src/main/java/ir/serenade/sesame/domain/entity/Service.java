package ir.serenade.sesame.domain.entity;

import ir.serenade.sesame.domain.base.BaseDomain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Service extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    @Column(unique = true)
    String uuid;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    Set<Product> products;


    public Service() {
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
