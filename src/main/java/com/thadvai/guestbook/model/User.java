package com.thadvai.guestbook.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

@Entity(name="user")
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required.")
    private String name;

    @NotEmpty(message = "Password is required.")
    private String password;

    @Transient
    private String passwordConfirmation;

    @Column
    private Boolean enabled;

    private Calendar created = Calendar.getInstance();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", 
    	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
    	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles;
    
    @OneToMany(
    		mappedBy = "user",
    		cascade = CascadeType.ALL,
    		orphanRemoval = true
    )
    private List<GuestbookEntry> entries = new ArrayList();
	
    public User() {
        super();
        this.enabled = false;
    }
    
    public User(String name) {
        super();
        this.name = name;
        this.enabled = true;
    }
    
    public User(String name, String password, boolean enabled, Collection<Role> roles) {
        super();
        this.name = name;
        this.password = password;
        this.enabled = enabled;
        this.created = Calendar.getInstance();
        this.roles = roles;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getCreated() {
        return this.created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(final String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Collection<Role> roles) {
        this.roles = roles;
    }
    
    @Override
    public String toString() {
        return "User{" + "id=" + id + '\'' + ", name='" + name + '\'' + ", password='" + password + '\'' + ", passwordConfirmation='" + passwordConfirmation + '\'' + ", created=" + created + ", enabled=" + enabled + '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((created == null) ? 0 : created.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (created == null) {
            if (other.created != null)
                return false;
        } else if (!created.equals(other.created))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (enabled == null) {
            if (other.enabled != null)
                return false;
        } else if (!enabled.equals(other.enabled))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        return true;
    }
}
