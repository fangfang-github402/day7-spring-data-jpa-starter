package com.oocl.springbootemployee.service;

import com.oocl.springbootemployee.model.Company;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.repository.CompanyInMemoryRepository;
import com.oocl.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyInMemoryRepository companyInMemoryRepository;
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyInMemoryRepository companyInMemoryRepository, CompanyRepository companyRepository) {
        this.companyInMemoryRepository = companyInMemoryRepository;
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public List<Company> findAll(Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
//        List<Company> companiesInPage = companyRepository.findAll(pageable).getContent();
        return companyRepository.findAll(pageable).getContent();
    }

    public Company findById(Integer id) {
        return companyRepository.findById(id).get();
    }


    public List<Employee> getEmployeesByCompanyId(Integer id) {
        Company company = companyRepository.findById(id).get();
        return company.getEmployees();
    }

    public Company create(Company company) {
        return companyRepository.save(company);
    }

    public Company update(Integer id, Company company) {
        final var companyNeedToUpdate = companyRepository
                .findById(id).get();

        var nameToUpdate = company.getName() == null ? companyNeedToUpdate.getName() : company.getName();
        var employeesToUpdate = company.getEmployees() == null ? companyNeedToUpdate.getEmployees() : company.getEmployees();

        final var companyToUpdate = new Company(id, nameToUpdate, employeesToUpdate);
        return companyInMemoryRepository.updateCompany(id, companyToUpdate);
    }

    public void delete(Integer id) {
        companyRepository.deleteById(id);
    }
}
