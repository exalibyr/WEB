package com.excalibur.myBlog.dao;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "file", schema = "public")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_id_seq")
    @SequenceGenerator(name = "file_id_seq", sequenceName = "file_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(value = {CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "publication_id")
    private Publication publication;

    public File() {
    }

    public File(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }
}
