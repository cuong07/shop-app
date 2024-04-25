package com.project.shopapp.responses;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ListResponse<T> {
    private List<T> contents;
    private int totalPages;
}
