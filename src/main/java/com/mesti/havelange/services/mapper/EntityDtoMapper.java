package com.mesti.havelange.services.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class EntityDtoMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <D, T> D map(T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    public static <D, T> List<D> mapAll(Iterable<T> entities, Class<D> outCLass) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }

    public static <S, T> T map(S source, T destination) {
        modelMapper.map(source, destination);
        return destination;
    }
}

