package ci.saydos.markazcompta.dao.repository.customize;

import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Locale;

import ci.saydos.markazcompta.utils.enums.StatutDemandeEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ci.saydos.markazcompta.utils.*;
import ci.saydos.markazcompta.utils.dto.*;
import ci.saydos.markazcompta.utils.contract.*;
import ci.saydos.markazcompta.utils.contract.Request;
import ci.saydos.markazcompta.utils.contract.Response;
import ci.saydos.markazcompta.dao.entity.*;

/**
 * Repository customize : Demande.
 */
@Repository
public interface _DemandeRepository {
    default List<String> _generateCriteria(DemandeDto dto, HashMap<String, java.lang.Object> param, Integer index, Locale locale) throws Exception {
        List<String> listOfQuery = new ArrayList<String>();

        // PUT YOUR RIGHT CUSTOM CRITERIA HERE

        return listOfQuery;
    }

    @Query("select e from Demande e where e.code = :code and e.statut = :statut and e.isDeleted = :isDeleted")
    Demande findByCodeAndStatut(@Param("code") String code, @Param("statut") StatutDemandeEnum statut, @Param("isDeleted") Boolean isDeleted);

    @Query("select e from Demande e where e.utilisateur.id = :idUtilisateur and e.label = :label and e.statut = :statut and e.direction.id = :idDirection and e.montant = :montant and e.isDeleted = :isDeleted")
    Demande findByStatutAndDirectionAndMontant(@Param("idUtilisateur") Integer idUtilisateur, @Param("label") String label, @Param("statut") StatutDemandeEnum statut, @Param("idDirection") Integer idDirection, @Param("montant") Double montant, @Param("isDeleted") Boolean isDeleted);

    @Query("select e from Demande e where e.id = :id and e.statut = :statut and e.isDeleted = :isDeleted")
    Demande findByIdAndStatut(@Param("id") Integer id, @Param("statut") StatutDemandeEnum statut, @Param("isDeleted") Boolean isDeleted);


}
