package ci.saydos.markazcompta.dao.repository.customize;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Locale;

import ci.saydos.markazcompta.utils.enums.TypeCaisseEnum;
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
 * Repository customize : Caisse.
 */
@Repository
public interface _CaisseRepository {
	default List<String> _generateCriteria(CaisseDto dto, HashMap<String, java.lang.Object> param, Integer index, Locale locale) throws Exception {
		List<String> listOfQuery = new ArrayList<String>();

		// PUT YOUR RIGHT CUSTOM CRITERIA HERE

		return listOfQuery;
	}
	@Query("select sum(c.montantEntre) from Caisse c where c.isDeleted = :isDeleted")
	Double montantTotalEntre(@Param("isDeleted") Boolean isDeleted) ;
	@Query("select sum(c.montantSortie) from Caisse c where c.isDeleted = :isDeleted")
	Double montantTotalSortie(@Param("isDeleted") Boolean isDeleted);
}
