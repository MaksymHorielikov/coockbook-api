package com.example.cookbookrestapi.dto.mapper;

public interface DtoMapper<M, Q, S> {
    M toModel(Q dto);

    S toDto(M model);
}
