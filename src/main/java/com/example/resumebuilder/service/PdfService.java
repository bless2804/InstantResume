package com.example.resumebuilder.service;

import com.example.resumebuilder.model.Resume;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * service that turns a Resume object into a simple one-page PDF
 * layout is kept basic and clean for readability
 */
@Service
public class PdfService {

    public byte[] generate(Resume resume) throws IOException {
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            PDPage page = new PDPage(PDRectangle.LETTER);
            document.addPage(page);

            try (PDPageContentStream content = new PDPageContentStream(document, page)) {
                float marginLeft = 50f;
                float yPosition = page.getMediaBox().getHeight() - 60f;

                // name section
                content.setFont(PDType1Font.HELVETICA_BOLD, 20);
                yPosition = drawLine(content, marginLeft, yPosition, getValue(resume.getName(), "Blessy Priyanka Rampogu"));

                // contact info
                content.setFont(PDType1Font.HELVETICA, 10);
                yPosition = drawLine(content, marginLeft, yPosition - 6,
                        "Ottawa, ON | " + getValue(resume.getPhone(), "647-xxx-xxxx") + " | " + getValue(resume.getEmail(), "example@email.com"));


                        yPosition -= 8;
                drawLineSeparator(content, marginLeft, yPosition,
                        page.getMediaBox().getWidth() - 2 * marginLeft);
                yPosition -= 12;

                // summary
                yPosition = drawSection(content, "SUMMARY", marginLeft, yPosition);
                yPosition = drawParagraph(content, marginLeft, yPosition,
                        getValue(resume.getSummary(), "Highly motivated and detail-oriented Computer Science student seeking a placement as a Software Developer or Quality Assurance Tester, eager to apply strong Java programming skills, analytical thinking, and collaborative problem-solving in a dynamic technical environment."));

                // education
                yPosition = drawSection(content, "EDUCATION", marginLeft, yPosition);
                yPosition = drawParagraph(content, marginLeft, yPosition,
                        getValue(resume.getEducation(), "B.C.S., Carleton University — 2026."));

                // experience
                yPosition = drawSection(content, "EXPERIENCE", marginLeft, yPosition);
                yPosition = drawBullets(content, marginLeft, yPosition,
                        getValue(resume.getExperience(), "Bob company — Developer Intern\nResidence Fellow — Carleton University"));

            }

            document.save(output);
            return output.toByteArray();
        }
    }

    // draws one line of text and moves the y position up for next line
    private float drawLine(PDPageContentStream content, float x, float y, String text) throws IOException {
        content.beginText();
        content.newLineAtOffset(x, y);
        content.showText(text);
        content.endText();
        return y - 16;
    }

    // draws a horizontal line separator
    private void drawLineSeparator(PDPageContentStream content, float x, float y, float width) throws IOException {
        content.moveTo(x, y);
        content.lineTo(x + width, y);
        content.setLineWidth(0.6f);
        content.stroke();
    }

    // draws section title in bold, then switches back to normal font
    private float drawSection(PDPageContentStream content, String title, float x, float y) throws IOException {
        content.setFont(PDType1Font.HELVETICA_BOLD, 12);
        y = drawLine(content, x, y, title);
        content.setFont(PDType1Font.HELVETICA, 10);
        return y;
    }

    // draws wrapped paragraph text
    private float drawParagraph(PDPageContentStream content, float x, float y, String text) throws IOException {
        int wrap = 95;
        int i = 0;
        while (i < text.length()) {
            int end = Math.min(i + wrap, text.length());
            y = drawLine(content, x, y, text.substring(i, end));
            i = end;
        }
        return y - 4;
    }

    // draws bullet points for each line
    private float drawBullets(PDPageContentStream content, float x, float y, String lines) throws IOException {
        for (String line : lines.split("\n")) {
            if (!line.isBlank()) {
                y = drawLine(content, x, y, "• " + line.trim());
            }
        }
        return y - 4;
    }

    // returns the resume field if filled, otherwise uses a fallback
    private String getValue(String value, String fallback) {
        return (value == null || value.isBlank()) ? fallback : value;
    }
}
