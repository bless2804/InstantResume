
package com.example.resumebuilder.repository;

import com.example.resumebuilder.model.Resume;
import com.example.resumebuilder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Optional<Resume> findByUser(User user);
}
