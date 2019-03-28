package com.urlshortener.ox.Entities;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(schema = "urlshortener")
public class Role implements GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private long id;

  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name="id",insertable = false,updatable = false)
  private long userId;

  @Column
  private String role;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String getAuthority() {
    return role;
  }
}
