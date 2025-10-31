package com.nigam.databasecontroller.controller;

import com.nigam.databasecontroller.entity.EquityGroup;
import com.nigam.databasecontroller.repository.EquityGroupRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equity-groups")
public class EquityGroupController {

    private final EquityGroupRepository repository;

    public EquityGroupController(EquityGroupRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<EquityGroup> create(@RequestBody EquityGroup request) {
        EquityGroup saved = repository.save(request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<EquityGroup> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{groupName}/{tickerName}")
    public ResponseEntity<EquityGroup> getById(@PathVariable String groupName, @PathVariable String tickerName) {
        return repository.findById(new EquityGroup.EquityGroupId(groupName, tickerName))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
