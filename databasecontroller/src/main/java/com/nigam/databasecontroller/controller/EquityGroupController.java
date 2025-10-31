package com.nigam.databasecontroller.controller;

import com.nigam.databasecontroller.entity.EquityGroup;
import com.nigam.databasecontroller.repository.EquityGroupRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equity-groups")
public class EquityGroupController {

    private final EquityGroupRepository repository;

    public EquityGroupController(EquityGroupRepository repository) {
        this.repository = repository;
    }

    // POST - Create via JSON
    @PostMapping
    public ResponseEntity<EquityGroup> create(@RequestBody EquityGroup request) {
        EquityGroup saved = repository.save(request);
        return ResponseEntity.ok(saved);
    }

    // GET - Fetch all
    @GetMapping
    public List<EquityGroup> getAll() {
        return repository.findAll();
    }

    // GET - By composite ID
    @GetMapping("/getById/{groupName}/{tickerName}")
    public ResponseEntity<EquityGroup> getById(@PathVariable String groupName, @PathVariable String tickerName) {
        return repository.findById(new EquityGroup.EquityGroupId(groupName, tickerName))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🆕 GET - Create or update record (Upsert)
    @GetMapping("/createNewEntry/{groupName}/{tickerName}/{groupType}")
    public ResponseEntity<EquityGroup> createOrUpdateEntry(
            @PathVariable String groupName,
            @PathVariable String tickerName,
            @PathVariable String groupType) {

        EquityGroup.EquityGroupId id = new EquityGroup.EquityGroupId(groupName, tickerName);
        Optional<EquityGroup> existing = repository.findById(id);

        EquityGroup entity;
        if (existing.isPresent()) {
            // Update case
            entity = existing.get();
            entity.setGroupType(groupType);
        } else {
            // Insert case
            entity = new EquityGroup(groupName, tickerName, groupType);
        }

        EquityGroup saved = repository.save(entity);
        return ResponseEntity.ok(saved);
    }

    // 🗑️ GET - Delete only if all 3 match
    @GetMapping("/deleteNewEntry/{groupName}/{tickerName}/{groupType}")
    public ResponseEntity<String> deleteEntryIfMatchesAll(
            @PathVariable String groupName,
            @PathVariable String tickerName,
            @PathVariable String groupType) {

        EquityGroup.EquityGroupId id = new EquityGroup.EquityGroupId(groupName, tickerName);
        Optional<EquityGroup> existing = repository.findById(id);

        if (existing.isEmpty()) {
            return ResponseEntity.ok("No entry found for groupName=" + groupName + ", tickerName=" + tickerName);
        }

        EquityGroup entity = existing.get();
        if (entity.getGroupType() != null && entity.getGroupType().equalsIgnoreCase(groupType)) {
            repository.deleteById(id);
            return ResponseEntity.ok("Deleted entry with groupName=" + groupName +
                    ", tickerName=" + tickerName +
                    ", groupType=" + groupType);
        } else {
            return ResponseEntity.ok("Entry found but groupType mismatch. Expected=" + entity.getGroupType() +
                    ", Provided=" + groupType + " → Not deleted.");
        }
    }
}
