package com.coursemate.controller;

import com.coursemate.model.Administrator;
import com.coursemate.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/administrators")
public class AdministratorController {

    @Autowired
    private AdministratorServiceImpl administratorService;

    @GetMapping
    public List<Administrator> getAllAdministrators() {
        return administratorService.getAllAdministrators();
    }

    @GetMapping("/{id}")
    public Optional<Administrator> getAdministratorById(@PathVariable Long id) {
        return administratorService.getAdministratorById(id);
    }

    @PostMapping
    public Administrator createAdministrator(@RequestBody Administrator administrator) {
        return administratorService.saveAdministrator(administrator);
    }

    @PutMapping("/{id}")
    public Administrator updateAdministrator(@PathVariable Long id, @RequestBody Administrator updatedAdministrator) {
        Optional<Administrator> existingAdministrator = administratorService.getAdministratorById(id);
        if (existingAdministrator.isPresent()) {
            updatedAdministrator.setId(id);
            return administratorService.saveAdministrator(updatedAdministrator);
        }
        throw new RuntimeException("Administrator not found with ID: " + id);
    }

    @DeleteMapping("/{id}")
    public void deleteAdministrator(@PathVariable Long id) {
        administratorService.deleteAdministrator(id);
    }
}
