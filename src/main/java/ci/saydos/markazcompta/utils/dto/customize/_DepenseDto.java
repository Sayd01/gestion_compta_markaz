
/*
 * Java dto for entity table depense
 * Created on 2023-08-10 ( Time 14:07:29 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils.dto.customize;

import ci.saydos.markazcompta.utils.dto.ChargeFixeDto;
import ci.saydos.markazcompta.utils.dto.DemandeDto;
import ci.saydos.markazcompta.utils.dto.UtilisateurDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * DTO customize for table "depense"
 *
 * @author Geo
 */
@Data
@JsonInclude(Include.NON_NULL)
public class _DepenseDto {
    private DemandeDto     demande;
    private ChargeFixeDto  chargeFixe;
    private UtilisateurDto userInfoCreator;
    private UtilisateurDto userInfoPaying;
}
