package com.airbyte.daneshmal.company;

import com.airbyte.daneshmal.common.ParentService;
import com.airbyte.daneshmal.dto.CategoryDTO;
import com.airbyte.daneshmal.dto.CategoryWithPersianNameDTO;
import com.airbyte.daneshmal.dto.CompanyDTO;
import com.airbyte.daneshmal.models.Company;
import com.airbyte.daneshmal.models.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CompanyService extends ParentService<Company, CompanyRepository, CompanyDTO> {

    public CompanyService(CompanyRepository repository) {
        super(repository);
    }

    @Override
    public Company updateModelFromDto(Company company, CompanyDTO dto) {
        company.setCompanyName(dto.getCompanyName() != null ? dto.getCompanyName() : company.getCompanyName());
        company.setCompanyFullName(dto.getCompanyFullName() != null ? dto.getCompanyFullName() : company.getCompanyFullName());
        company.setLogoUrl(dto.getLogoUrl() != null ? dto.getLogoUrl() : company.getLogoUrl());
        company.setRedirectUrl(dto.getRedirectUrl() != null ? dto.getRedirectUrl() : company.getRedirectUrl());
        company.setCategoryOriginalName(dto.getCategory() != null ? dto.getCategory() : company.getCategoryOriginalName());
        return company;
    }

    @Override
    public Company convertDTO(CompanyDTO dto) {
        Company company = new Company();
        company.setCompanyName(dto.getCompanyName());
        company.setCompanyFullName(dto.getCompanyFullName());
        company.setLogoUrl(dto.getLogoUrl());
        company.setRedirectUrl(dto.getRedirectUrl());
        company.setCategoryOriginalName(dto.getCategory());
        return company;
    }

    @Override
    public List<Company> getWithSearch(CompanyDTO search) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Company> criteriaBuilderQuery = criteriaBuilder.createQuery(Company.class);

        Root<Company> root = criteriaBuilderQuery.from(Company.class);
        List<Predicate> predicates = new ArrayList<>();

        if (search.getCategory() != null) {
            predicates.add(criteriaBuilder.equal(root.get("category"), search.getCategory()));
        }

        criteriaBuilderQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaBuilderQuery).getResultList();
    }

    public CategoryWithPersianNameDTO getByCategory(String category, Pageable pageable) {
        CategoryWithPersianNameDTO response = new CategoryWithPersianNameDTO();
        CompanyDTO dto = new CompanyDTO();
        dto.setCategory(category);

        List<Company> modelList = this.getWithSearch(dto);

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), modelList.size());
        final Page<Company> page = new PageImpl<>(modelList.subList(start, end), pageable, modelList.size());
        response.setData(page);
        response.setPersianName(Category.valueOf(category).getPersianName());
        return response;
    }

    public List<Company> getByCategory(String category) {
        CompanyDTO dto = new CompanyDTO();
        dto.setCategory(category);

        return this.getWithSearch(dto);
    }

    public List<CategoryDTO> getCategories() {
        List<CategoryDTO> resultList = new ArrayList<>();

        for (Category category : Category.values()) {
            CategoryDTO dto = new CategoryDTO();
            dto.setName(category.name());
            dto.setPersianName(category.getPersianName());
            resultList.add(dto);
        }

        return resultList;
    }

    @Override
    protected List<Company> postFetch(List<Company> companies) {
        return companies
                .stream()
                .sorted(Comparator.comparing(Company::getDate).reversed())
                .toList();
    }
}
