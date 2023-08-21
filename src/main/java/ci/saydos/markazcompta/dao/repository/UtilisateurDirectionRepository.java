package ci.saydos.markazcompta.dao.repository;

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
import ci.saydos.markazcompta.dao.repository.customize._UtilisateurDirectionRepository;

/**
 * Repository : UtilisateurDirection.
 */
@Repository
public interface UtilisateurDirectionRepository extends JpaRepository<UtilisateurDirection, Integer>, _UtilisateurDirectionRepository {
	/**
	 * Finds UtilisateurDirection by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object UtilisateurDirection whose id is equals to the given id. If
	 *         no UtilisateurDirection is found, this method returns null.
	 */
	@Query("select e from UtilisateurDirection e where e.id = :id")
	UtilisateurDirection findOne(@Param("id")Integer id);


	/**
	 * Finds UtilisateurDirection by using idDirection as a search criteria.
	 * 
	 * @param idDirection
	 * @return An Object UtilisateurDirection whose idDirection is equals to the given idDirection. If
	 *         no UtilisateurDirection is found, this method returns null.
	 */
	@Query("select e from UtilisateurDirection e where e.direction.id = :idDirection")
	List<UtilisateurDirection> findByIdDirection(@Param("idDirection")Integer idDirection);

	/**
	 * Finds UtilisateurDirection by using idUtilisateur as a search criteria.
	 * 
	 * @param idUtilisateur
	 * @return An Object UtilisateurDirection whose idUtilisateur is equals to the given idUtilisateur. If
	 *         no UtilisateurDirection is found, this method returns null.
	 */
	@Query("select e from UtilisateurDirection e where e.utilisateur.id = :idUtilisateur")
	List<UtilisateurDirection> findByIdUtilisateur(@Param("idUtilisateur")Integer idUtilisateur);

	/**
	 * Finds List of UtilisateurDirection by using utilisateurDirectionDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of UtilisateurDirection
	 * @throws DataAccessException,ParseException
	 */
	default List<UtilisateurDirection> getByCriteria(Request<UtilisateurDirectionDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from UtilisateurDirection e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<UtilisateurDirection> query = em.createQuery(req, UtilisateurDirection.class);
		for (Map.Entry<String, java.lang.Object> entry : param.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		if (request.getIndex() != null && request.getSize() != null) {
			query.setFirstResult(request.getIndex() * request.getSize());
			query.setMaxResults(request.getSize());
		}
		return query.getResultList();
	}

	/**
	 * Finds count of UtilisateurDirection by using utilisateurDirectionDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of UtilisateurDirection
	 * 
	 */
	default Long count(Request<UtilisateurDirectionDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from UtilisateurDirection e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by  e.id desc";
		jakarta.persistence.Query query = em.createQuery(req);
		for (Map.Entry<String, java.lang.Object> entry : param.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		Long count = (Long) query.getResultList().get(0);
		return count;
	}

	/**
	 * get where expression
	 * @param request
	 * @param param
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	default String getWhereExpression(Request<UtilisateurDirectionDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		UtilisateurDirectionDto dto = request.getData() != null ? request.getData() : new UtilisateurDirectionDto();
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (UtilisateurDirectionDto elt : request.getDatas()) {
				String eltReq = generateCriteria(elt, param, index, locale);
				if (request.getIsAnd() != null && request.getIsAnd()) {
					othersReq += "and (" + eltReq + ") ";
				} else {
					othersReq += "or (" + eltReq + ") ";
				}
				index++;
			}
		}
		String req = "";
		if (!mainReq.isEmpty()) {
			req += " and (" + mainReq + ") ";
		}
		req += othersReq;
		return req;
	}

	/**
	 * generate sql query for dto
	 * @param dto
	 * @param param
	 * @param index
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	default String generateCriteria(UtilisateurDirectionDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (dto.getIdDirection()!= null && dto.getIdDirection() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idDirection", dto.getIdDirection(), "e.direction.id", "Integer", dto.getIdDirectionParam(), param, index, locale));
			}
			if (dto.getIdUtilisateur()!= null && dto.getIdUtilisateur() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idUtilisateur", dto.getIdUtilisateur(), "e.utilisateur.id", "Integer", dto.getIdUtilisateurParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getDirectionCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("directionCode", dto.getDirectionCode(), "e.direction.code", "String", dto.getDirectionCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getDirectionIntitule())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("directionIntitule", dto.getDirectionIntitule(), "e.direction.intitule", "String", dto.getDirectionIntituleParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getUtilisateurLogin())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("utilisateurLogin", dto.getUtilisateurLogin(), "e.utilisateur.login", "String", dto.getUtilisateurLoginParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getUtilisateurFirstName())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("utilisateurFirstName", dto.getUtilisateurFirstName(), "e.utilisateur.firstName", "String", dto.getUtilisateurFirstNameParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getUtilisateurLastName())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("utilisateurLastName", dto.getUtilisateurLastName(), "e.utilisateur.lastName", "String", dto.getUtilisateurLastNameParam(), param, index, locale));
			}
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
