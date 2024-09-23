package br.com.essia.virtufile.service;

import br.com.essia.virtufile.dto.FileRequest;
import br.com.essia.virtufile.dto.FileResponse;
import br.com.essia.virtufile.exception.BadRequestNotFoundException;
import br.com.essia.virtufile.model.Directory;
import br.com.essia.virtufile.model.File;
import br.com.essia.virtufile.repository.DirectoryRepository;
import br.com.essia.virtufile.repository.FileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final DirectoryRepository directoryRepository;

    public FileService(FileRepository fileRepository, DirectoryRepository directoryRepository) {
        this.fileRepository = fileRepository;
        this.directoryRepository = directoryRepository;
    }

    public FileResponse create(FileRequest request) {
        if (request.getDirectoryId() != null) {
            Directory directory = directoryRepository.findById(request.getDirectoryId())
                    .orElseThrow(() -> new BadRequestNotFoundException(404, "Diretório não encontrado com o ID: " + request.getDirectoryId()));

            File file = File.builder()
                    .name(request.getName())
                    .directory(directory)
                    .build();

            fileRepository.save(file);

            return FileResponse.builder()
                    .id(file.getId())
                    .name(file.getName())
                    .directoryName(file.getDirectory().getName())
                    .build();
        }

        File file = File.builder()
                .name(request.getName())
                .directory(null)
                .build();

        fileRepository.save(file);

        return FileResponse.builder()
                .id(file.getId())
                .name(file.getName())
                .directoryName(null)
                .build();
    }

    public FileResponse update(Long id, FileRequest request) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new BadRequestNotFoundException(404, "Arquivo não encontrado com o ID: " + id));

        if (request.getDirectoryId() != null){
            Directory directory = directoryRepository.findById(request.getDirectoryId())
                    .orElseThrow(() -> new BadRequestNotFoundException(404, "Diretório não encontrado com o ID: " + request.getDirectoryId()));

            file.setDirectory(directory);
        } else {
            file.setDirectory(null);
        }

        file.setName(request.getName());

        File updatedFile = fileRepository.save(file);

        return FileResponse.builder()
                .id(updatedFile.getId())
                .name(updatedFile.getName())
                .directoryName(updatedFile.getDirectory().getName())
                .build();
    }

    public void delete(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new BadRequestNotFoundException(404, "Arquivo não encontrado com o ID: " + id));

        fileRepository.delete(file);
    }

    public List<FileResponse> listAll() {
        List<File> fileList = fileRepository.findAll();
        return fileList.stream()
                .map(file -> FileResponse.builder()
                        .id(file.getId())
                        .name(file.getName())
                        .directoryName(file.getDirectory().getName())
                        .build())
                .collect(Collectors.toList());
    }
}
