
package com.example.resumebuilder.controller;

import com.example.resumebuilder.model.Resume;
import com.example.resumebuilder.model.User;
import com.example.resumebuilder.service.AuthService;
import com.example.resumebuilder.service.PdfService;
import com.example.resumebuilder.service.ResumeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController @RequestMapping("/api/resume")
public class ResumeController {
    private final AuthService authService; private final ResumeService resumeService; private final PdfService pdfService;
    public ResumeController(AuthService a, ResumeService r, PdfService p){ this.authService=a; this.resumeService=r; this.pdfService=p; }

    @PutMapping("/{email}")
    public ResponseEntity<?> upsert(@PathVariable("email") String email, @RequestBody Resume incoming){
        User user = authService.byEmail(email).orElseGet(()->{
            User u=new User(); u.setEmail(email); u.setName(incoming.getName()==null? "New User": incoming.getName()); u.setPassword("auto"); return authService.save(u);
        });
        incoming.setEmail(email);
        System.out.println("Received email: " + incoming.getEmail());
        incoming.setUser(user);
        var saved = resumeService.upsert(user, incoming);
        return ResponseEntity.ok(saved.getId());
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> get(@PathVariable("email") String email){
        return authService.byEmail(email).flatMap(resumeService::getByUser).<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/{email}/pdf")
    public ResponseEntity<byte[]> pdf(@PathVariable("email") String email) throws IOException {
        // get the user by email
        var foundUser = authService.byEmail(email);
        if (foundUser.isEmpty()) return ResponseEntity.notFound().build();

        // get the resume belonging to that user
        var foundResume = resumeService.getByUser(foundUser.get());
        if (foundResume.isEmpty()) return ResponseEntity.notFound().build();

        // generate the pdf
        byte[] pdfBytes = pdfService.generate(foundResume.get());

        // return it as a downloadable file
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"InstantResume.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }


    @PostMapping("/generate")
    // this endpoint creates bullet points for given job titles in a simple, deterministic way
    public ResponseEntity<List<Map<String, Object>>> generate(@RequestBody Map<String, Object> payload) {
        Object jobsObj = payload.get("jobs");
        String program = String.valueOf(payload.getOrDefault("program",""));
        if (!(jobsObj instanceof List)) {
            return ResponseEntity.badRequest().build();
        }
        @SuppressWarnings("unchecked")
        List<String> jobs = (List<String>) jobsObj;
        List<Map<String, Object>> out = new ArrayList<>();
        for (String job : jobs) {
            List<String> bullets = resumeService.generateBullets(job, program);
            Map<String, Object> entry = new HashMap<>();
            entry.put("job", job);
            entry.put("bullets", bullets);
            out.add(entry);
        }
        return ResponseEntity.ok(out);
    }
}
