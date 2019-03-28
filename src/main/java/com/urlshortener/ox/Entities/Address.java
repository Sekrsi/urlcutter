package com.urlshortener.ox.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;

@Entity
@Table(name = "website",schema = "urlshortener")
public class Address {

    public Address() {
    }

    public Address(String url, String HEXvalue) {
        this.url = url;
        this.HEXvalue = HEXvalue;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    Integer urlID;

    @Column(name = "url")
    private String url;

    @Getter
    @Setter
    @Column(name = "HEXvalue")
    private String HEXvalue;




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
}
