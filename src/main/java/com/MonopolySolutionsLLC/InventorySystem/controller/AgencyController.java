package com.MonopolySolutionsLLC.InventorySystem.controller;

import com.MonopolySolutionsLLC.InventorySystem.exception.ResourceNotFoundException;
import com.MonopolySolutionsLLC.InventorySystem.model.Agency;
import com.MonopolySolutionsLLC.InventorySystem.model.Enums.AgencyLevel;
import com.MonopolySolutionsLLC.InventorySystem.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/agencies")
public class AgencyController {

    @Autowired
    private AgencyService agencyService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAgency(@RequestBody Agency agency) {

        if (agency.getLevel() == null) {
            return ResponseEntity.badRequest().body("Error: Level is required.");
        }

        // Check if the provided level string matches any AgencyLevel enum, case-insensitively
        boolean levelExists = Arrays.stream(AgencyLevel.values())
                .anyMatch(e -> e.name().equalsIgnoreCase(agency.getLevel().name()));

        if (!levelExists) {
            return ResponseEntity.badRequest().body("Error: Invalid level value.");
        }

        return ResponseEntity.ok(agencyService.saveAgency(agency));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Agency>> getAllAgencies() {
        return ResponseEntity.ok(agencyService.getAllAgencies());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Agency> getAgencyById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(agencyService.getAgencyById(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Agency> updateAgency(@PathVariable Long id, @RequestBody Agency agencyDetails) {
        try {
            return ResponseEntity.ok(agencyService.updateAgency(id, agencyDetails));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAgency(@PathVariable Long id) {
        try {
            agencyService.deleteAgency(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
