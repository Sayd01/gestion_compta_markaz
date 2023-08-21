package ci.saydos.markazcompta.dao.repository;

import java.util.Date;
import java.util.List;
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
import ci.saydos.markazcompta.dao.repository.customize._UtilisateurRepository;

/**
 * Repository : Utilisateur.
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer>, _UtilisateurRepository {
	/**
	 * Finds Utilisateur by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object Utilisateur whose id is equals to the given id. If
	 *         no Utilisateur is found, this method returns null.
	 */
	@Query("select e from Utilisateur e where e.id = :id and e.isDeleted = :isDeleted")
	Utilisateur findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Utilisateur by using login as a search criteria.
	 * 
	 * @param login
	 * @return An Object Utilisateur whose login is equals to the given login. If
	 *         no Utilisateur is found, this method returns null.
	 */
	@Query("select e from Utilisateur e where e.login = :login and e.isDeleted = :isDeleted")
	Utilisateur findByLogin(@Param("login")String login, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Utilisateur by using firstName as a search criteria.
	 * 
	 * @param firstName
	 * @return An Object Utilisateur whose firstName is equals to the given firstName. If
	 *         no Utilisateur is found, this method returns null.
	 */
	@Query("select e from Utilisateur e where e.firstName = :firstName and e.isDeleted = :isDeleted")
	List<Utilisateur> findByFirstName(@Param("firstName")String firstName, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Utilisateur by using lastName as a search criteria.
	 * 
	 * @param lastName
	 * @return An Object Utilisateur whose lastName is equals to the given lastName. If
	 *         no Utilisateur is found, this method returns null.
	 */
	@Query("select e from Utilisateur e where e.lastName = :lastName and e.isDeleted = :isDeleted")
	List<Utilisateur> findByLastName(@Param("lastName")String lastName, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Utilisateur by using email as a search criteria.
	 * 
	 * @param email
	 * @return An Object Utilisateur whose email is equals to the given email. If
	 *         no Utilisateur is found, this method returns null.
	 */
	@Query("select e from Utilisateur e where e.email = :email and e.isDeleted = :isDeleted")
	Utilisateur findByEmail(@Param("email")String email, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Utilisateur by using imageUrl as a search criteria.
	 * 
	 * @param imageUrl
	 * @return An Object Utilisateur whose imageUrl is equals to the given imageUrl. If
	 *         no Utilisateur is found, this method returns null.
	 */
	@Query("select e from Utilisateur e where e.imageUrl = :imageUrl and e.isDeleted = :isDeleted")
	List<Utilisateur> findByImageUrl(@Param("imageUrl")String imageUrl, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Utilisateur by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object Utilisateur whose createdAt is equals to the given createdAt. If
	 *         no Utilisateur is found, this method returns null.
	 */
	@Query("select e from Utilisateur e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<Utilisateur> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Utilisateur by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object Utilisateur whose createdBy is equals to the given createdBy. If
	 *         no Utilisateur is found, this method returns null.
	 */
	@Query("select e from Utilisateur e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<Utilisateur> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Utilisateur by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object Utilisateur whose isDeleted is equals to the given isDeleted. If
	 *         no Utilisateur is found, this method returns null.
	 */
	@Query("select e from Utilisateur e where e.isDeleted = :isDeleted")
	List<Utilisateur> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Utilisateur by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object Utilisateur whose updatedAt is equals to the given updatedAt. If
	 *         no Utilisateur is found, this method returns null.
	 */
	@Query("select e from Utilisateur e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<Utilisateur> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Utilisateur by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object Utilisateur whose updatedBy is equals to the given updatedBy. If
	 *         no Utilisateur is found, this method returns null.
	 */
	@Query("select e from Utilisateur e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<Utilisateur> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Utilisateur by using password as a search criteria.
	 * 
	 * @param password
	 * @return An Object Utilisateur whose password is equals to the given password. If
	 *         no Utilisateur is found, this method returns null.
	 */
	@Query("select e from Utilisateur e where e.password = :password and e.isDeleted = :isDeleted")
	List<Utilisateur> findByPassword(@Param("password")String password, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Utilisateur by using idMarkaz as a search criteria.
	 * 
	 * @param idMarkaz
	 * @return An Object Utilisateur whose idMarkaz is equals to the given idMarkaz. If
	 *         no Utilisateur is found, this method returns null.
	 */
	@Query("select e from Utilisateur e where e.markaz.id = :idMarkaz and e.isDeleted = :isDeleted")
	List<Utilisateur> findByIdMarkaz(@Param("idMarkaz")Integer idMarkaz, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of Utilisateur by using utilisateurDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of Utilisateur
	 * @throws DataAccessException,ParseException
	 */
	default List<Utilisateur> getByCriteria(Request<UtilisateurDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from Utilisateur e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<Utilisateur> query = em.createQuery(req, Utilisateur.class);
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
	 * Finds count of Utilisateur by using utilisateurDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of Utilisateur
	 * 
	 */
	default Long count(Request<UtilisateurDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from Utilisateur e where e IS NOT NULL";
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
	default String getWhereExpression(Request<UtilisateurDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		UtilisateurDto dto = request.getData() != null ? request.getData() : new UtilisateurDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (UtilisateurDto elt : request.getDatas()) {
				elt.setIsDeleted(false);
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
	default String generateCriteria(UtilisateurDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLogin())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("login", dto.getLogin(), "e.login", "String", dto.getLoginParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getFirstName())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("firstName", dto.getFirstName(), "e.firstName", "String", dto.getFirstNameParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLastName())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("lastName", dto.getLastName(), "e.lastName", "String", dto.getLastNameParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getEmail())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("email", dto.getEmail(), "e.email", "String", dto.getEmailParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getImageUrl())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("imageUrl", dto.getImageUrl(), "e.imageUrl", "String", dto.getImageUrlParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCreatedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("createdAt", dto.getCreatedAt(), "e.createdAt", "Date", dto.getCreatedAtParam(), param, index, locale));
			}
			if (dto.getCreatedBy()!= null && dto.getCreatedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("createdBy", dto.getCreatedBy(), "e.createdBy", "Integer", dto.getCreatedByParam(), param, index, locale));
			}
			if (dto.getIsDeleted()!= null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("isDeleted", dto.getIsDeleted(), "e.isDeleted", "Boolean", dto.getIsDeletedParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getUpdatedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedAt", dto.getUpdatedAt(), "e.updatedAt", "Date", dto.getUpdatedAtParam(), param, index, locale));
			}
			if (dto.getUpdatedBy()!= null && dto.getUpdatedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedBy", dto.getUpdatedBy(), "e.updatedBy", "Integer", dto.getUpdatedByParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getPassword())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("password", dto.getPassword(), "e.password", "String", dto.getPasswordParam(), param, index, locale));
			}
			if (dto.getIdMarkaz()!= null && dto.getIdMarkaz() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idMarkaz", dto.getIdMarkaz(), "e.markaz.id", "Integer", dto.getIdMarkazParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getMarkazCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("markazCode", dto.getMarkazCode(), "e.markaz.code", "String", dto.getMarkazCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getMarkazIntitule())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("markazIntitule", dto.getMarkazIntitule(), "e.markaz.intitule", "String", dto.getMarkazIntituleParam(), param, index, locale));
			}
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
