package com.example.resumebuilder.service;

import com.example.resumebuilder.model.Resume;
import com.example.resumebuilder.model.User;
import com.example.resumebuilder.repository.ResumeRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

/**
 * this service handles resume creation, updates, and ai-based bullet generation.
 */
@Service
public class ResumeService {

    private final ResumeRepository repo;

    // connect to the ai assistant class that talks to openai
    @Autowired
    private ResumeAIAssistant resumeAIAssistant;

    public ResumeService(ResumeRepository repo) {
        this.repo = repo;
    }

    /**
     * this method creates or updates a resume for a given user
     */
    public Resume upsert(User user, Resume in) {
        Resume resume = repo.findByUser(user).orElseGet(Resume::new);
        resume.setUser(user);
        resume.setName(in.getName());
        resume.setPhone(in.getPhone());
        resume.setAddress(in.getAddress());
        resume.setSummary(in.getSummary());
        resume.setEducation(in.getEducation());
        resume.setExperience(in.getExperience());
        resume.setSkills(in.getSkills());
        resume.setProjects(in.getProjects());
        resume.setEmail(in.getEmail());

        return repo.save(resume);
    }

    /**
     * this method returns the resume of a specific user if it exists
     */
    public Optional<Resume> getByUser(User u) {
        return repo.findByUser(u);
    }

    /**
     * this method generates 3 resume bullet points using ai instead of random templates
     * it sends a prompt with job title and program to openai and gets short, natural bullets
     */
    public List<String> generateBullets(String jobTitle, String program) {
        try {
            // ask ai to generate bullets for this job title and program
            return resumeAIAssistant.generateBullets(jobTitle, program);
        } catch (Exception e) {
            // if ai call fails, return a simple fallback so app still works
            List<String> fallback = new ArrayList<>();
            fallback.add("Developed solutions for " + jobTitle + " using modern tools.");
            fallback.add("Collaborated in team projects to improve performance and quality.");
            fallback.add("Gained practical experience in " + program + " while contributing to outcomes.");
            return fallback;
        }
    }
}
