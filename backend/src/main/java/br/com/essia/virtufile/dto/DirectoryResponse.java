package br.com.essia.virtufile.dto;

import br.com.essia.virtufile.model.Directory;
import br.com.essia.virtufile.model.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectoryResponse {
    private Long id;
    private String name;
    private List<File> files;
    private List<Directory> subdirectories;
}
