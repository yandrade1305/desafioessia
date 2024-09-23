package br.com.essia.virtufile.controller;

import br.com.essia.virtufile.dto.DirectoryRequest;
import br.com.essia.virtufile.dto.DirectoryResponse;
import br.com.essia.virtufile.dto.DirectoryUpdateRequest;
import br.com.essia.virtufile.service.DirectoryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/directory")
public class DirectoryController {
    private final DirectoryService directoryService;

    public DirectoryController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @PostMapping("/create")
    public DirectoryResponse createDirectory(@RequestBody DirectoryRequest request) {
        return directoryService.create(request);
    }

    @PatchMapping("/update/{id}")
    public DirectoryResponse updateDirectory(@PathVariable Long id, @RequestBody DirectoryUpdateRequest request){
        return directoryService.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDirectory(@PathVariable Long id){
        directoryService.delete(id);
    }

    @GetMapping("/get")
    public List<DirectoryResponse> listAllDirectories() {
        return  directoryService.listAll();
    }
}
