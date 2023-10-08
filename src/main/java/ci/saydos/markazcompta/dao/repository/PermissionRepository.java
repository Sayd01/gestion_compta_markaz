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
import ci.saydos.markazcompta.dao.repository.customize._PermissionRepository;

/**
 * Repository : Permission.
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer>, _PermissionRepository {
	/**
	 * Finds Permission by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object Permission whose id is equals to the given id. If
	 *         no Permission is found, this method returns null.
	 */
	@Query("select e from Permission e where e.id = :id and e.isDeleted = :isDeleted")
	Permission findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Permission by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object Permission whose createdAt is equals to the given createdAt. If
	 *         no Permission is found, this method returns null.
	 */
	@Query("select e from Permission e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<Permission> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Permission by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object Permission whose createdBy is equals to the given createdBy. If
	 *         no Permission is found, this method returns null.
	 */
	@Query("select e from Permission e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<Permission> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Permission by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object Permission whose isDeleted is equals to the given isDeleted. If
	 *         no Permission is found, this method returns null.
	 */
	@Query("select e from Permission e where e.isDeleted = :isDeleted")
	List<Permission> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Permission by using name as a search criteria.
	 * 
	 * @param name
	 * @return An Object Permission whose name is equals to the given name. If
	 *         no Permission is found, this method returns null.
	 */
	@Query("select e from Permission e where e.name = :name and e.isDeleted = :isDeleted")
	Permission findByName(@Param("name")String name, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Permission by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object Permission whose updatedAt is equals to the given updatedAt. If
	 *         no Permission is found, this method returns null.
	 */
	@Query("select e from Permission e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<Permission> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Permission by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object Permission whose updatedBy is equals to the given updatedBy. If
	 *         no Permission is found, this method returns null.
	 */
	@Query("select e from Permission e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<Permission> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of Permission by using permissionDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of Permission
	 * @throws DataAccessException,ParseException
	 */
	default List<Permission> getByCriteria(Request<PermissionDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from Permission e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<Permission> query = em.createQuery(req, Permission.class);
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
	 * Finds count of Permission by using permissionDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of Permission
	 * 
	 */
	default Long count(Request<PermissionDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from Permission e where e IS NOT NULL";
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
	default String getWhereExpression(Request<PermissionDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		PermissionDto dto = request.getData() != null ? request.getData() : new PermissionDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (PermissionDto elt : request.getDatas()) {
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
	default String generateCriteria(PermissionDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
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
			if (Utilities.notBlank(dto.getName())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("name", dto.getName(), "e.name", "String", dto.getNameParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getUpdatedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedAt", dto.getUpdatedAt(), "e.updatedAt", "Date", dto.getUpdatedAtParam(), param, index, locale));
			}
			if (dto.getUpdatedBy()!= null && dto.getUpdatedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedBy", dto.getUpdatedBy(), "e.updatedBy", "Integer", dto.getUpdatedByParam(), param, index, locale));
			}
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
