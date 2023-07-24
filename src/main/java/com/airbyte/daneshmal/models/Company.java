package com.airbyte.daneshmal.models;


import com.airbyte.daneshmal.models.enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;


@Entity
@Table
public class Company implements Serializable {
    @Id
    private @Column(columnDefinition = "VARCHAR(50)", nullable = false) String id;
    private @Column(columnDefinition = "VARCHAR(255)") String companyName;
    private @Column(columnDefinition = "VARCHAR(255)") String companyFullName;
    private @Column(columnDefinition = "VARCHAR(255)") String logoUrl;
    private @JsonIgnore
    @Column(columnDefinition = "VARCHAR(255)") String category;
    private @Column(columnDefinition = "VARCHAR(255)") String redirectUrl;


    public String getId() {
        return id;
    }

    @PrePersist
    public void setId() {
        this.id = UUID.randomUUID().toString().replace("-", "");
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyFullName() {
        return companyFullName;
    }

    public void setCompanyFullName(String companyFullName) {
        this.companyFullName = companyFullName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @JsonProperty("category")
    public String persianName() {
        return Category.valueOf(this.category).getPersianName();
    }
}
