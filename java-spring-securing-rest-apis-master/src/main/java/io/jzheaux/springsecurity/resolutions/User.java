package io.jzheaux.springsecurity.resolutions;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Entity(name = "users")
public class User implements Serializable {
    @Id
    UUID id;

    @Column
    String username;

    @Column
    String fullName;

    @Column
    String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Collection<UserAuthority> userAuthorities = new ArrayList<>();

    @Column
    boolean enabled = true;


    public User() {
    }

    public User(String username, String password) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
    }

    public User(User user) {
        this.id = user.id;
        this.username = user.username;
        this.password = user.password;
        this.fullName = user.fullName;
        this.userAuthorities = user.userAuthorities;
        this.enabled = user.enabled;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<UserAuthority> getUserAuthorities() {
        return Collections.unmodifiableCollection(this.userAuthorities);
    }

    public void grantAuthority(String authority) {
        UserAuthority userAuthority = new UserAuthority(authority, this);
        this.userAuthorities.add(userAuthority);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
