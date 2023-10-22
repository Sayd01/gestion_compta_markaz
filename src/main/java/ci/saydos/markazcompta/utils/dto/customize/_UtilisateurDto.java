
/*
 * Java dto for entity table utilisateur
 * Created on 2023-08-08 ( Time 19:02:56 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils.dto.customize;

import ci.saydos.markazcompta.utils.dto.DirectionDto;
import ci.saydos.markazcompta.utils.dto.RoleDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO customize for table "utilisateur"
 *
 * @author Geo
 */
@JsonInclude(Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class _UtilisateurDto {
    private List<DirectionDto> directions;
    private List<RoleDto>      roles;
    private Boolean            isFirstConnexion;
}
