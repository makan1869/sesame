package ir.serenade.sesame.domain.entity;

import ir.serenade.sesame.domain.base.BaseDomain;

import javax.persistence.*;

@Entity
public class Device  extends BaseDomain{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    User user;

    String uuid;

    String deviceName;

    String deviceType;

    String deviceOS;

    public Device() {
    }

    public Device(User user, String deviceType, String deviceOS) {
        this.user = user;
        this.deviceType = deviceType;
        this.deviceOS = deviceOS;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceOS() {
        return deviceOS;
    }

    public void setDeviceOS(String deviceOS) {
        this.deviceOS = deviceOS;
    }


}
