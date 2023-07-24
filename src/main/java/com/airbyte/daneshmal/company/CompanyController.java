package com.airbyte.daneshmal.company;

import com.airbyte.daneshmal.dto.CategoryDTO;
import com.airbyte.daneshmal.dto.CompanyDTO;
import com.airbyte.daneshmal.models.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.airbyte.daneshmal.security.permission.ManagePermission.COMPANY_WRITE;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {

    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize(COMPANY_WRITE)
    public ResponseEntity<Company> saveCompany(@RequestBody CompanyDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize(COMPANY_WRITE)
    public ResponseEntity<Company> updateCompany(@PathVariable String id, @RequestBody CompanyDTO dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(COMPANY_WRITE)
    public ResponseEntity<Void> deleteCompany(@PathVariable String id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{category}")
    public ResponseEntity<Page<Company>> getCompanyByCategory(@PathVariable String category, Pageable pageable) {
        return new ResponseEntity<>(service.getByCategory(category, pageable), HttpStatus.CREATED);
    }

    @GetMapping("/{category}/findAll")
    public ResponseEntity<List<Company>> getCompanyByCategory(@PathVariable String category) {
        return new ResponseEntity<>(service.getByCategory(category), HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    @PreAuthorize(COMPANY_WRITE)
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return new ResponseEntity<>(service.getCategories(), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<Page<Company>> getAllCompanies(Pageable pageable) {
        return new ResponseEntity<>(service.getAll(pageable), HttpStatus.OK);
    }
}
