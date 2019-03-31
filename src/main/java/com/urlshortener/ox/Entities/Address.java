package com.urlshortener.ox.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

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
    @Column(name = "HEXvalue")
    private String HEXvalue;

    public Address() {
    }

    public Address(User user, String url, String HEXvalue) {
        this.user = user;
        this.url = url;
        this.HEXvalue = HEXvalue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUrlID() {
        return urlID;
    }

    public void setUrlID(Integer urlID) {
        this.urlID = urlID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHEXvalue() {
        return HEXvalue;
    }

    public void setHEXvalue(String HEXvalue) {
        this.HEXvalue = HEXvalue;
    }

}
