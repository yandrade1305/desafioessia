package br.com.essia.virtufile.controller;

import br.com.essia.virtufile.dto.DirectoryUpdateRequest;
import br.com.essia.virtufile.dto.FileRequest;
import br.com.essia.virtufile.dto.FileResponse;
import br.com.essia.virtufile.service.FileService;
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
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/create")
    public FileResponse createFile(@RequestBody FileRequest request) {
        return fileService.create(request);
    }

    @PatchMapping("/update/{id}")
    public FileResponse updateFile(@PathVariable Long id, @RequestBody FileRequest request){
        return fileService.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFile(@PathVariable Long id){
        fileService.delete(id);
    }

    @GetMapping("/get")
    public List<FileResponse> listAllFiles() {
        return  fileService.listAll();
    }
}
