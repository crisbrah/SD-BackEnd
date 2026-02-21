package com.codigo.sanidaddivina.infrastructure.adapter.out.persistence.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IglesiaMapperConfig {
}
