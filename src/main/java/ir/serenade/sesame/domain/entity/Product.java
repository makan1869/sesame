package ir.serenade.sesame.domain.entity;

import ir.serenade.sesame.domain.base.BaseDomain;

import javax.persistence.*;

@Entity
public class Product  extends BaseDomain{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    @Column(unique = true)
    String uuid;
    Type type;
    @ManyToOne(fetch = FetchType.EAGER)
    Service service;

    public Product() {
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public static enum Type {
        FREE, ONE_TIME, CONSUMABLE, TIMELY, TIMELY_WITH_RENEWAL
    }
}
