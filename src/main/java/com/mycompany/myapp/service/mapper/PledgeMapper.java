package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PledgeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pledge} and its DTO {@link PledgeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PledgeMapper extends EntityMapper<PledgeDTO, Pledge> {



    default Pledge fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pledge pledge = new Pledge();
        pledge.setId(id);
        return pledge;
    }
}
