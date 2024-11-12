package com.coursemate.service;

import com.coursemate.model.Administrator;
import com.coursemate.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorServiceImpl{

    @Autowired
    private AdministratorRepository administratorRepository;


    public List<Administrator> getAllAdministrators() {
        return administratorRepository.findAll();
    }


    public Optional<Administrator> getAdministratorById(Long id) {
        return administratorRepository.findById(id);
    }


    public Administrator saveAdministrator(Administrator administrator) {
        return administratorRepository.save(administrator);
    }


    public void deleteAdministrator(Long id) {
        administratorRepository.deleteById(id);
    }

    // Add methods if administrators need to access or manage grades
}
