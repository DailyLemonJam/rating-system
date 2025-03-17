package com.leverx.ratingsystem.mapper;

import java.util.List;

public interface ModelDtoMapper<Dto, Model> {
    Dto toDto(Model model);
    Model toModel(Dto dto);
    List<Dto> toDto(List<Model> models);
    List<Model> toModel(List<Dto> dtos);
}
