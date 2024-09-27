package br.com.essia.virtufile.controller;

import br.com.essia.virtufile.service.MockMvcService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FileControllerTest extends BaseControllerTest{

    @Autowired
    private MockMvcService mockMvcService;

    @Test
    @Order(1)
    void shouldCreateFile() throws Exception {
        mockMvcService.post("/api/file/create", """
                              {
                                "name": "file.txt",
                                "directoryId": 1
                              }
                              """)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value((4)))
                .andExpect(jsonPath("$.name").value(("file.txt")))
                .andExpect(jsonPath("$.directoryName").value(("Raiz")));
    }

    @Test
    @Order(2)
    void shouldUpdateFile() throws Exception {
        mockMvcService.patch("/api/file/update/4", """
                             {
                                "name": "fileupdated.txt",
                                "directoryId": 2
                              }
                             """)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value((4)))
                .andExpect(jsonPath("$.name").value(("fileupdated.txt")))
                .andExpect(jsonPath("$.directoryName").value(("Documentos")));
    }

    @Test
    @Order(3)
    void shouldDeleteFile() throws Exception {
        mockMvcService.delete("/api/file/delete/4")
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Order(4)
    void shouldGetAllFiles() throws Exception {
        mockMvcService.get("/api/file/get")
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("arquivo1.txt"))
                .andExpect(jsonPath("$[0].directoryName").value("Documentos"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("imagem1.png"))
                .andExpect(jsonPath("$[1].directoryName").value("Imagens"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("projeto.docx"))
                .andExpect(jsonPath("$[2].directoryName").value("Trabalho"));

    }
}
