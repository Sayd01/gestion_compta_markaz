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

import ci.saydos.markazcompta.utils.enums.TypeDepenseEnum;
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
 * Repository customize : Depense.
 */
@Repository
public interface _DepenseRepository {
	default List<String> _generateCriteria(DepenseDto dto, HashMap<String, java.lang.Object> param, Integer index, Locale locale) throws Exception {
		List<String> listOfQuery = new ArrayList<String>();

		// PUT YOUR RIGHT CUSTOM CRITERIA HERE

		return listOfQuery;
	}

	@Query("select e from Depense e " +
			"where function('MONTH',e.createdAt)  = :mois and function('YEAR',e.createdAt)  = :annee " +
			"and e.chargeFixe.id = :idChargeFixe and e.montant = :montant " +
			"and e.isDeleted = :isDeleted")
	Depense findByDateAndIdChargeAndMontant(@Param("mois") Integer mois,
											@Param("annee") Integer annee,
											@Param("idChargeFixe") Integer idChargeFixe,
											@Param("montant") Double montant, @Param("isDeleted") Boolean isDeleted);
	@Query("select e from Depense e where  e.demande.id = :idDemande and e.typeDepense = :typeDepense and e.isDeleted = :isDeleted")
	Depense findByIdDemandeAndType(@Param("idDemande")Integer idDemande, @Param("typeDepense") TypeDepenseEnum typeDepense, @Param("isDeleted") Boolean isDeleted );

	@Query("select e from Depense e where  e.demande.id = :idDemande and e.isDeleted = :isDeleted and e.isCompleted = :isCompleted")
	Depense findByIdDemandeAndType(@Param("idDemande")Integer idDemande, @Param("isDeleted") Boolean isDeleted, @Param("isCompleted") Boolean isCompleted );
	@Query("select e from Depense e where e.chargeFixe.id = :idChargeFixe and e.isDeleted = :isDeleted and e.isCompleted = :isCompleted")
	List<Depense> findByIdChargeFixeAndIsCompleted(@Param("idChargeFixe")Integer idChargeFixe, @Param("isDeleted") Boolean isDeleted, @Param("isCompleted") Boolean isCompleted);

	@Query("SELECT e FROM Depense e WHERE e.chargeFixe.id = :idChargeFixe " +
			"AND e.isDeleted = :isDeleted AND e.isCompleted = :isCompleted " +
			"AND MONTH(e.createdAt) = :currentMonth")
	Depense findByIdChargeFixeAndIsCompletedAndCurrentMonth(
			@Param("idChargeFixe") Integer idChargeFixe,
			@Param("isDeleted") Boolean isDeleted,
			@Param("isCompleted") Boolean isCompleted,
			@Param("currentMonth") Integer currentMonth);

}
