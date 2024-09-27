package br.com.essia.virtufile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectoryUpdateRequest {
    private String name;
    private Long parentId;
    private List<Long> filesIds;
    private List<Long> subdirectoriesIds;
}
