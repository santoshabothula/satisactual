package com.datawise.satisactual.service;

import com.datawise.satisactual.exception.SatisActualProcessException;
import com.datawise.satisactual.repository.BaseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommonMasterService<DTO, ENTITY, ID> {

    @Transactional(readOnly = true)
    public List<DTO> getAll(BaseRepository<ENTITY, ID> repository, ModelMapper mapper, Class<DTO> dto) {
        return repository.findAll().stream().map(o -> mapper.map(o, dto)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DTO get(BaseRepository<ENTITY, ID> repository, ModelMapper mapper, Class<DTO> dto, ID id) {
        return mapper.map(getEntity(repository, id), dto);
    }

    @Transactional
    public void save(BaseRepository<ENTITY, ID> repository, ENTITY entity) {
        repository.save(entity);
    }

    @Transactional(readOnly = true)
    public ENTITY beforeSave(BaseRepository<ENTITY, ID> repository, ModelMapper mapper, Class<ENTITY> entity, ID id, DTO dto) {
        if (Objects.nonNull(getOptionalEntity(repository, id))) {
            throw new SatisActualProcessException("Record already exists!");
        }
        return mapper.map(dto, entity);
    }

    @Transactional(readOnly = true)
    public ENTITY beforeUpdate(BaseRepository<ENTITY, ID> repository, ModelMapper mapper, Class<ENTITY> entity, ID id, DTO dto) {
        if (Objects.isNull(getOptionalEntity(repository, id))) {
            throw new SatisActualProcessException("Record does not exists!");
        }
        return mapper.map(dto, entity);
    }

    @Transactional
    public void delete(BaseRepository<ENTITY, ID> repository, ID id) {
        ENTITY entity = getOptionalEntity(repository, id);
        if (Objects.isNull(entity)) throw new SatisActualProcessException("Record not found!");
        repository.delete(entity);
    }

    private ENTITY getEntity(BaseRepository<ENTITY, ID> repository, ID id) {
        ENTITY entity = getOptionalEntity(repository, id);
        if (Objects.isNull(entity)) throw new SatisActualProcessException("Record not found!");
        return entity;
    }

    private ENTITY getOptionalEntity(BaseRepository<ENTITY, ID> repository, ID id) {
        return repository.findById(id).orElse(null);
    }
}
