package com.urlshortener.ox.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;


@Entity
@Table(schema = "urlshortener")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY )
  @Column
  private long ID;

  @Column
  @NotBlank
  @Size(min = 4, max = 30)
  private String username;

  @Column
  @Email
  @NotNull
  private String email;

  @Column
  @JsonIgnore
  @NotBlank
  @Size(min = 8, max = 30)
  private String password;

  @ManyToOne(targetEntity = Role.class)
  @JoinColumn(name="ROLE",insertable = false,updatable = false)
  @JsonManagedReference
  private Role role;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
  @JsonBackReference
  private Set<Address> addresses;

  @Column
  private boolean enabled;

  @Column
  private boolean activated;

  public User(String username, @Email String email, String password, Role role, Set<Address> addresses, boolean enabled, boolean activated) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
    this.addresses = addresses;
    this.enabled = enabled;
    this.activated = activated;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public boolean isActivated() {
    return activated;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Set<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(Set<Address> addresses) {
    this.addresses = addresses;
  }

  public User() {
  }

  public long getID() {
    return ID;
  }

  public void setID(long ID) {
    this.ID = ID;
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
