package com.urlshortener.ox.Entities;


import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;


@Entity
@Table(schema = "urlshortener")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY )
  @Column
  private long id;

  @Column
  private String username;

  @Column
  @Email
  private String email;

  @Column
  private String password;

  @OneToMany(mappedBy = "role")
  private Set<Role> role;

  public Set<Role> getRole() {
    return role;
  }

  public void setRole(Set<Role> role) {
    this.role = role;
  }

  @Column
  private boolean enabled;

  @Column
  private boolean activated;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }


  public boolean getActivated() {
    return activated;
  }

  public void setActivated(boolean activated) {
    this.activated = activated;
  }

}
