package ci.saydos.markazcompta.dao.repository;

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
import ci.saydos.markazcompta.dao.repository.customize._DirectionRepository;

/**
 * Repository : Direction.
 */
@Repository
public interface DirectionRepository extends JpaRepository<Direction, Integer>, _DirectionRepository {
	/**
	 * Finds Direction by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object Direction whose id is equals to the given id. If
	 *         no Direction is found, this method returns null.
	 */
	@Query("select e from Direction e where e.id = :id and e.isDeleted = :isDeleted")
	Direction findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Direction by using code as a search criteria.
	 * 
	 * @param code
	 * @return An Object Direction whose code is equals to the given code. If
	 *         no Direction is found, this method returns null.
	 */
	@Query("select e from Direction e where e.code = :code and e.isDeleted = :isDeleted")
	Direction findByCode(@Param("code")String code, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Direction by using intitule as a search criteria.
	 * 
	 * @param intitule
	 * @return An Object Direction whose intitule is equals to the given intitule. If
	 *         no Direction is found, this method returns null.
	 */
	@Query("select e from Direction e where e.intitule = :intitule and e.isDeleted = :isDeleted")
	Direction findByIntitule(@Param("intitule")String intitule, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Direction by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object Direction whose createdAt is equals to the given createdAt. If
	 *         no Direction is found, this method returns null.
	 */
	@Query("select e from Direction e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<Direction> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Direction by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object Direction whose createdBy is equals to the given createdBy. If
	 *         no Direction is found, this method returns null.
	 */
	@Query("select e from Direction e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<Direction> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Direction by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object Direction whose isDeleted is equals to the given isDeleted. If
	 *         no Direction is found, this method returns null.
	 */
	@Query("select e from Direction e where e.isDeleted = :isDeleted")
	List<Direction> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Direction by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object Direction whose updatedAt is equals to the given updatedAt. If
	 *         no Direction is found, this method returns null.
	 */
	@Query("select e from Direction e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<Direction> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Direction by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object Direction whose updatedBy is equals to the given updatedBy. If
	 *         no Direction is found, this method returns null.
	 */
	@Query("select e from Direction e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<Direction> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Direction by using idMarkaz as a search criteria.
	 * 
	 * @param idMarkaz
	 * @return An Object Direction whose idMarkaz is equals to the given idMarkaz. If
	 *         no Direction is found, this method returns null.
	 */
	@Query("select e from Direction e where e.markaz.id = :idMarkaz and e.isDeleted = :isDeleted")
	List<Direction> findByIdMarkaz(@Param("idMarkaz")Integer idMarkaz, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of Direction by using directionDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of Direction
	 * @throws DataAccessException,ParseException
	 */
	default List<Direction> getByCriteria(Request<DirectionDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from Direction e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<Direction> query = em.createQuery(req, Direction.class);
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
	 * Finds count of Direction by using directionDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of Direction
	 * 
	 */
	default Long count(Request<DirectionDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from Direction e where e IS NOT NULL";
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
	default String getWhereExpression(Request<DirectionDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		DirectionDto dto = request.getData() != null ? request.getData() : new DirectionDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (DirectionDto elt : request.getDatas()) {
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
	default String generateCriteria(DirectionDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("code", dto.getCode(), "e.code", "String", dto.getCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getIntitule())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("intitule", dto.getIntitule(), "e.intitule", "String", dto.getIntituleParam(), param, index, locale));
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
