package com.urlshortener.ox.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "website", schema = "urlshortener")
public class Address {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    Integer urlID;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user",insertable = false,updatable = false)
    @JsonManagedReference
    private User user;


    @Column(name = "url")
    private String url;

    @Column
    private boolean active = true;

    @Column
    private Date setupDATE;

    @Column
    private Date expDATE;

    public Address() {
    }

    public Address(User user, String url, boolean active, Date setupDATE, Date expDATE) {
        this.user = user;
        this.url = url;
        this.active = active;
        this.setupDATE = setupDATE;
        this.expDATE = expDATE;
    }

    public Integer getUrlID() {
        return urlID;
    }

    public void setUrlID(Integer urlID) {
        this.urlID = urlID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getSetupDATE() {
        return setupDATE;
    }

    public void setSetupDATE(Date setupDATE) {
        this.setupDATE = setupDATE;
    }

    public Date getExpDATE() {
        return expDATE;
    }

    public void setExpDATE(Date expDATE) {
        this.expDATE = expDATE;
    }
}
