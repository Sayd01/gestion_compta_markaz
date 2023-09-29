
/*
 * Java dto for entity table demande_historique 
 * Created on 2023-08-06 ( Time 01:31:05 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils.dto.customize;

import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ci.saydos.markazcompta.utils.contract.*;
import lombok.Data;

/**
 * DTO customize for table "demande_historique"
 * 
 * @author Geo
 *
 */
@JsonInclude(Include.NON_NULL)
@Data
public class _DemandeHistoriqueDto {
    private String granularite;
}
