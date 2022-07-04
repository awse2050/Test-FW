package com.example.javatest.study;

import com.example.javatest.domain.Study;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StudyController {

    private final StudyRepository repository;

    @GetMapping("/study/{id}")
    public Study getStudy(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Study not found " + id + "..."));
    }

    @PostMapping("/study")
    public Study createStudy(@RequestBody Study study) {
        return repository.save(study);
    }
}
