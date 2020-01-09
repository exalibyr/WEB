package com.excalibur.myBlog.dao;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role", schema = "public")
public class Role implements GrantedAuthority{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    @SequenceGenerator(name = "role_id_seq", sequenceName = "role_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "role", nullable = false, insertable = false, length = 10)
    private String role;


    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    Set<VerificationData> verificationDataSet;

    public Role() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<VerificationData> getVerificationDataSet() {
        return verificationDataSet;
    }

    public void setVerificationDataSet(Set<VerificationData> verificationDataSet) {
        this.verificationDataSet = verificationDataSet;
    }

    @Override
    public String getAuthority() {
        return getRole();
    }
}
