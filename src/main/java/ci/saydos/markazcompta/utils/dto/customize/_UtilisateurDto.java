
/*
 * Java dto for entity table utilisateur 
 * Created on 2023-08-08 ( Time 19:02:56 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils.dto.customize;

import java.util.Date;
import java.util.List;
import java.util.Date;

import ci.saydos.markazcompta.dao.entity.Direction;
import ci.saydos.markazcompta.utils.dto.DirectionDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ci.saydos.markazcompta.utils.contract.*;
import lombok.Data;

/**
 * DTO customize for table "utilisateur"
 * 
 * @author Geo
 *
 */
@JsonInclude(Include.NON_NULL)
@Data
public class _UtilisateurDto {
    private List<DirectionDto> directions;
}
