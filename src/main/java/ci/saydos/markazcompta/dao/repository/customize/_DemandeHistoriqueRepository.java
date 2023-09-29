package ci.saydos.markazcompta.dao.repository.customize;

import java.util.Date;
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
 * Repository customize : DemandeHistorique.
 */
@Repository
public interface _DemandeHistoriqueRepository {
    default List<String> _generateCriteria(DemandeHistoriqueDto dto, HashMap<String, java.lang.Object> param, Integer index, Locale locale) throws Exception {
        List<String> listOfQuery = new ArrayList<String>();

        // PUT YOUR RIGHT CUSTOM CRITERIA HERE

        return listOfQuery;
    }

    @Query("select e from DemandeHistorique e where e.utilisateur.id = :idUtilisateur and e.demande.id = :idDemande and e.statut = :statut and e.isDeleted = :isDeleted")
    DemandeHistorique findByUserAndDemandeAndStatut(@Param("idUtilisateur") Integer idUtilisateur, @Param("idDemande") Integer idDemande, @Param("statut") StatutDemandeEnum statut, @Param("isDeleted") Boolean isDeleted);

    @Query("SELECT d.statut, COUNT(*) " +
            "FROM DemandeHistorique dh " +
            "JOIN Demande d ON dh.id = d.id " +
            "WHERE :dateParam IS NULL OR dh.createdAt = :dateParam " +
            "OR (:dateDebut IS NULL AND :dateFin IS NULL) OR (dh.createdAt BETWEEN :dateDebut AND :dateFin) " +
            "GROUP BY d.statut")
    List<Object[]> countDemandesByTypeAndDate(
            @Param("dateParam") String dateParam,
            @Param("dateDebut") String dateDebut,
            @Param("dateFin") String dateFin);


}
