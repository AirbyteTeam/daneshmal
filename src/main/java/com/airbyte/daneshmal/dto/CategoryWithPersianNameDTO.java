package com.airbyte.daneshmal.dto;

import com.airbyte.daneshmal.models.Company;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public class CategoryWithPersianNameDTO implements Serializable {
    private Page<Company> data;
    private String persianName;


    public Page<Company> getData() {
        return data;
    }

    public void setData(Page<Company> data) {
        this.data = data;
    }

    public String getPersianName() {
        return persianName;
    }

    public void setPersianName(String persianName) {
        this.persianName = persianName;
    }
}
