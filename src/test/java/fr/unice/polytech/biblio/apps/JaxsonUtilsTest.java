package fr.unice.polytech.biblio.apps;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.unice.polytech.biblio.api.JaxsonUtils;
import fr.unice.polytech.biblio.api.dtos.StudentDTO;
import fr.unice.polytech.biblio.entities.Etudiant;
import fr.unice.polytech.biblio.entities.Livre;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JaxsonUtilsTest {

    @Test
    void testAStudentToJson() throws JsonProcessingException {
        Etudiant etudiant = new Etudiant();
        etudiant.setName("John Doe");
        etudiant.setStudentNumber(123456);
        String json = JaxsonUtils.toJson(etudiant);
        assertNotNull(json);
        assertTrue(json.contains("\"name\":\"John Doe\""));
        assertTrue(json.contains("\"studentNumber\":123456"));
    }

    @Test
    void testFromJsonToStudent() {
        String json = "{\"name\":\"John Doe\",\"studentNumber\":123456}";
        Etudiant etudiant = JaxsonUtils.fromJson(json, Etudiant.class);
        assertNotNull(etudiant);
        assertEquals("John Doe", etudiant.getName());
        assertEquals(123456, etudiant.getStudentNumber());
    }

    @Test
    void testABookToJson() throws JsonProcessingException {
        Livre livre = new Livre("Design Patterns", new String[] { "Erich Gamma" }, "1994", 0);
        String json = JaxsonUtils.toJson(livre);
        assertNotNull(json);
        assertTrue(json.contains("\"titre\":\"Design Patterns\""));
        assertTrue(json.contains("\"auteurs\":[\"Erich Gamma\"]"));
        assertTrue(json.contains("\"isbn\":\"1994\""));
    }

    @Test
    void testFromJsonToBook() throws JsonProcessingException {
        String json = "{\"titre\":\"Design Patterns\",\"auteurs\":[\"Erich Gamma\"],\"isbn\":\"1994\",\"idDansBiblio\":\"DP-0\"}";
        Livre livre = JaxsonUtils.fromJson(json, Livre.class);
        assertNotNull(livre);
        assertEquals("Design Patterns", livre.getTitre());
        assertEquals("1994", livre.getIsbn());
        assertEquals("DP-0", livre.getIdDansBiblio());

        livre = new Livre("Your Code as a Crime Scene", new String[] { "Adam Tornhill" }, "123");
        String newBook = JaxsonUtils.toJson(livre);
        livre = JaxsonUtils.fromJson(newBook, Livre.class);
        assertNotNull(livre);
    }

    @Test
    void testFromStudentDTOToJson() throws JsonProcessingException {
        StudentDTO studentDTO = new StudentDTO(123456);
        String json = JaxsonUtils.toJson(studentDTO);
        assertNotNull(json);
        assertTrue(json.contains("\"studentNumber\":123456"));
    }

    @Test
    void testFromJsonToStudentDTO() {
        String json = "{\"studentNumber\":123456}";
        StudentDTO studentDTO = JaxsonUtils.fromJson(json, StudentDTO.class);
        assertNotNull(studentDTO);
        assertEquals(123456, studentDTO.studentNumber());
    }
}