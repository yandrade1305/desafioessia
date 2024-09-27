package br.com.essia.virtufile.controller;

import br.com.essia.virtufile.service.MockMvcService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class DirectoryControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvcService mockMvcService;

    @Test
    @Order(1)
    void shouldCreateDirectory() throws Exception {
        mockMvcService.post("/api/directory/create", """
                              {
                                "name": "directory",
                                "parentId": null
                              }
                              """)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value((5)))
                .andExpect(jsonPath("$.name").value(("directory")))
                .andExpect(jsonPath("$.files").value((nullValue())))
                .andExpect(jsonPath("$.subdirectories").value((nullValue())));
    }

    @Test
    @Order(2)
    void shouldUpdateDirectory() throws Exception {
        mockMvcService.patch("/api/directory/update/5", """
                             {
                                "name": "directory updated",
                                "parentId": null,
                                "filesIds": [2],
                                "subdirectoriesIds": [3]
                              }
                             """)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value((5)))
                .andExpect(jsonPath("$.name").value(("directory updated")))
                .andExpect(jsonPath("$.files[0].id").value((2)))
                .andExpect(jsonPath("$.files[0].name").value(("imagem1.png")))
                .andExpect(jsonPath("$.subdirectories[0].id").value((3)))
                .andExpect(jsonPath("$.subdirectories[0].name").value(("Imagens")))
                .andExpect(jsonPath("$.subdirectories[0].subdirectories").value(empty()))
                .andExpect(jsonPath("$.subdirectories[0].files[0].id").value((2)))
                .andExpect(jsonPath("$.subdirectories[0].files[0].name").value(("imagem1.png")));
    }

    @Test
    @Order(3)
    void shouldDeleteDirectory() throws Exception {
        mockMvcService.delete("/api/directory/delete/5")
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Order(4)
    void shouldGetAllDirectories() throws Exception {
        mockMvcService.get("/api/directory/get")
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Raiz"))
                .andExpect(jsonPath("$[0].files").isEmpty())
                .andExpect(jsonPath("$[0].subdirectories[0].id").value(2))
                .andExpect(jsonPath("$[0].subdirectories[0].name").value("Documentos"))
                .andExpect(jsonPath("$[0].subdirectories[0].files[0].id").value(1))
                .andExpect(jsonPath("$[0].subdirectories[0].files[0].name").value("arquivo1.txt"))
                .andExpect(jsonPath("$[0].subdirectories[0].subdirectories[0].id").value(4))
                .andExpect(jsonPath("$[0].subdirectories[0].subdirectories[0].name").value("Trabalho"))
                .andExpect(jsonPath("$[0].subdirectories[0].subdirectories[0].files[0].id").value(3))
                .andExpect(jsonPath("$[0].subdirectories[0].subdirectories[0].files[0].name").value("projeto.docx"))
                .andExpect(jsonPath("$[0].subdirectories[1].id").value(3))
                .andExpect(jsonPath("$[0].subdirectories[1].name").value("Imagens"))
                .andExpect(jsonPath("$[0].subdirectories[1].subdirectories").isEmpty())
                .andExpect(jsonPath("$[0].subdirectories[1].files[0].id").value(2))
                .andExpect(jsonPath("$[0].subdirectories[1].files[0].name").value("imagem1.png"));
    }

}
