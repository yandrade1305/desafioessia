package br.com.essia.virtufile.service;

import br.com.essia.virtufile.dto.DirectoryRequest;
import br.com.essia.virtufile.dto.DirectoryResponse;
import br.com.essia.virtufile.dto.DirectoryUpdateRequest;
import br.com.essia.virtufile.exception.BadRequestNotFoundException;
import br.com.essia.virtufile.model.Directory;
import br.com.essia.virtufile.model.File;
import br.com.essia.virtufile.repository.DirectoryRepository;
import br.com.essia.virtufile.repository.FileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DirectoryService {

    private final DirectoryRepository directoryRepository;
    private final FileRepository filesRepository;

    public DirectoryService(DirectoryRepository directoryRepository, FileRepository filesRepository) {
        this.directoryRepository = directoryRepository;
        this.filesRepository = filesRepository;
    }

    public DirectoryResponse create(DirectoryRequest request) {
        if (request.getParentId() != null) {
            Directory parentDirectory = directoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new BadRequestNotFoundException(404, "Diretório não encontrado com o ID: " + request.getParentId()));

            Directory directory = Directory.builder()
                    .name(request.getName())
                    .parent(parentDirectory)
                    .build();

            directoryRepository.save(directory);

            return DirectoryResponse.builder()
                    .id(directory.getId())
                    .name(directory.getName())
                    .files(directory.getFiles())
                    .subdirectories(directory.getSubdirectories())
                    .build();
        }

        Directory directory = Directory.builder()
                .name(request.getName())
                .build();

        directoryRepository.save(directory);

        return DirectoryResponse.builder()
                .id(directory.getId())
                .name(directory.getName())
                .files(directory.getFiles())
                .subdirectories(directory.getSubdirectories())
                .build();
    }

    public DirectoryResponse update(Long id, DirectoryUpdateRequest request) {
        List<File> fileList = new ArrayList<File>();
        List<Directory> directoryList = new ArrayList<Directory>();

        Directory directory = directoryRepository.findById(id)
                .orElseThrow(() -> new BadRequestNotFoundException(404, "Diretório não encontrado com o ID: " + id));

        if (request.getParentId() != null){
            Directory parentDirectory = directoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new BadRequestNotFoundException(404, "Diretório não encontrado com o ID: " + request.getParentId()));

            directory.setParent(parentDirectory);
        } else {
            directory.setParent(null);
        }

        directory.setName(request.getName());

        if (request.getFilesIds() != null){
            request.getFilesIds().forEach(
                    fileId -> {
                        File file = filesRepository.findById(fileId)
                                .orElseThrow(() -> new BadRequestNotFoundException(404, "Arquivo não encontrado com o ID: " + fileId));
                        fileList.add(file);
                    }
            );
            directory.setFiles(fileList);
        } else {
            directory.setFiles(null);
        }

        if (request.getSubdirectoriesIds() != null){
            request.getSubdirectoriesIds().forEach(
                    subdirectoriesId -> {
                        Directory subdirectory = directoryRepository.findById(subdirectoriesId)
                                .orElseThrow(() -> new BadRequestNotFoundException(404, "Diretório não encontrado com o ID: " + request.getParentId()));
                        directoryList.add(subdirectory);
                    }
            );
            directory.setSubdirectories(directoryList);
        } else {
            directory.setSubdirectories(null);
        }

        Directory updatedDirectory = directoryRepository.save(directory);

        return DirectoryResponse.builder()
                .id(updatedDirectory.getId())
                .name(updatedDirectory.getName())
                .files(updatedDirectory.getFiles())
                .subdirectories(updatedDirectory.getSubdirectories())
                .build();
    }

    public void delete(Long id) {
        Directory directory = directoryRepository.findById(id)
                .orElseThrow(() -> new BadRequestNotFoundException(404, "Diretório não encontrado com o ID: " + id));

        directoryRepository.delete(directory);
    }

    public List<DirectoryResponse> listAll() {
        List<Directory> directoryList = directoryRepository.findAll();

        List<Directory> rootDirectories = directoryList.stream()
                .filter(directory -> isRootDirectory(directory, directoryList))
                .collect(Collectors.toList());

        return rootDirectories.stream()
                .map(directory -> DirectoryResponse.builder()
                        .id(directory.getId())
                        .name(directory.getName())
                        .files(directory.getFiles())
                        .subdirectories(directory.getSubdirectories())
                        .build())
                .collect(Collectors.toList());
    }

    private boolean isRootDirectory(Directory directory, List<Directory> allDirectories) {
        return allDirectories.stream()
                .noneMatch(parent -> parent.getSubdirectories().contains(directory));
    }

}
