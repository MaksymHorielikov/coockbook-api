package com.example.cookbookrestapi.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "recipes")
@SQLDelete(sql = "UPDATE recipes SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private LocalDateTime dateCreated;
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Recipe parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Recipe> childRecipes = new ArrayList<>();

    public Recipe() {
    }

    public Recipe(Long id, String description, LocalDateTime dateCreated) {
        this.id = id;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    public void addChildRecipe(Recipe childRecipe) {
        childRecipes.add(childRecipe);
    }
}
