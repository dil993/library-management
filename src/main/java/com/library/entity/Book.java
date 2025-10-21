package com.library.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_ID")
    private Long id;

    @Column(name = "TITLE", nullable = false, length = 200)
    private String title;

    @Column(name = "AUTHOR", nullable = false, length = 100)
    private String author;

    @Column(name = "IS_AVAILABLE", nullable = false)
    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "CHECKED_OUT_BY", referencedColumnName = "user_id")
    private User checkedOutBy;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public User getCheckedOutBy() { return checkedOutBy; }
    public void setCheckedOutBy(User checkedOutBy) { this.checkedOutBy = checkedOutBy; }
}
