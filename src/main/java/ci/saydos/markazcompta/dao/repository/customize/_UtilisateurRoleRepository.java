package ci.saydos.markazcompta.dao.repository.customize;

import java.util.Date;
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
 * Repository customize : UtilisateurRole.
 */
@Repository
public interface _UtilisateurRoleRepository {
	default List<String> _generateCriteria(UtilisateurRoleDto dto, HashMap<String, java.lang.Object> param, Integer index, Locale locale) throws Exception {
		List<String> listOfQuery = new ArrayList<String>();

		// PUT YOUR RIGHT CUSTOM CRITERIA HERE

		return listOfQuery;
	}

	@Query("select e from UtilisateurRole e where e.utilisateur.email = :userEmail and e.role.name = :roleName and e.isDeleted = :isDeleted")
	UtilisateurRole findByUserAndRole(@Param("userEmail")String userEmail,@Param("roleName")String roleName, @Param("isDeleted")Boolean isDeleted);

	@Query("select e from UtilisateurRole e where e.utilisateur.email= :email")
	List<UtilisateurRole> findUserRoleByEmail(@Param("email")String email);
}
