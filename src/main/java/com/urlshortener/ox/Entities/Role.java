package com.urlshortener.ox.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(schema = "urlshortener")
public class Role implements GrantedAuthority {

  public Set<User> getUser() {
    return user;
  }

  public void setUser(Set<User> user) {
    this.user = user;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private long id;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
  @JsonBackReference
  private Set<User> user;

  @Column
  private String role;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Role(Set<User> user, String role) {
    this.user = user;
    this.role = role;
  }

  public Role() {
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String getAuthority() {
    return role;
  }
}
