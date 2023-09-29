package ci.saydos.markazcompta.dao.repository.customize;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Locale;
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
 * Repository customize : UtilisateurDirection.
 */
@Repository
public interface _UtilisateurDirectionRepository {
	default List<String> _generateCriteria(UtilisateurDirectionDto dto, HashMap<String, java.lang.Object> param, Integer index, Locale locale) throws Exception {
		List<String> listOfQuery = new ArrayList<String>();

		// PUT YOUR RIGHT CUSTOM CRITERIA HERE

		return listOfQuery;
	}
	@Query("select e from UtilisateurDirection e where e.utilisateur.email = :email and e.direction.code = :code and e.isDeleted = :isDeleted")
    UtilisateurDirection findByDirectionAndUser(@Param("email")String email,@Param("code")String code, @Param("isDeleted")Boolean isDeleted);
}
