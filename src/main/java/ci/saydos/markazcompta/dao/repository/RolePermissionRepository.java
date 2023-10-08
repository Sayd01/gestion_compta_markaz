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
import ci.saydos.markazcompta.dao.repository.customize._RolePermissionRepository;

/**
 * Repository : RolePermission.
 */
@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer>, _RolePermissionRepository {
	/**
	 * Finds RolePermission by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object RolePermission whose id is equals to the given id. If
	 *         no RolePermission is found, this method returns null.
	 */
	@Query("select e from RolePermission e where e.id = :id and e.isDeleted = :isDeleted")
	RolePermission findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds RolePermission by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object RolePermission whose createdAt is equals to the given createdAt. If
	 *         no RolePermission is found, this method returns null.
	 */
	@Query("select e from RolePermission e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<RolePermission> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds RolePermission by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object RolePermission whose createdBy is equals to the given createdBy. If
	 *         no RolePermission is found, this method returns null.
	 */
	@Query("select e from RolePermission e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<RolePermission> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds RolePermission by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object RolePermission whose isDeleted is equals to the given isDeleted. If
	 *         no RolePermission is found, this method returns null.
	 */
	@Query("select e from RolePermission e where e.isDeleted = :isDeleted")
	List<RolePermission> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds RolePermission by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object RolePermission whose updatedAt is equals to the given updatedAt. If
	 *         no RolePermission is found, this method returns null.
	 */
	@Query("select e from RolePermission e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<RolePermission> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds RolePermission by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object RolePermission whose updatedBy is equals to the given updatedBy. If
	 *         no RolePermission is found, this method returns null.
	 */
	@Query("select e from RolePermission e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<RolePermission> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds RolePermission by using permissionId as a search criteria.
	 * 
	 * @param permissionId
	 * @return An Object RolePermission whose permissionId is equals to the given permissionId. If
	 *         no RolePermission is found, this method returns null.
	 */
	@Query("select e from RolePermission e where e.permission.id = :permissionId and e.isDeleted = :isDeleted")
	List<RolePermission> findByPermissionId(@Param("permissionId")Integer permissionId, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds RolePermission by using roleId as a search criteria.
	 * 
	 * @param roleId
	 * @return An Object RolePermission whose roleId is equals to the given roleId. If
	 *         no RolePermission is found, this method returns null.
	 */
	@Query("select e from RolePermission e where e.role.id = :roleId and e.isDeleted = :isDeleted")
	List<RolePermission> findByRoleId(@Param("roleId")Integer roleId, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of RolePermission by using rolePermissionDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of RolePermission
	 * @throws DataAccessException,ParseException
	 */
	default List<RolePermission> getByCriteria(Request<RolePermissionDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from RolePermission e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<RolePermission> query = em.createQuery(req, RolePermission.class);
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
	 * Finds count of RolePermission by using rolePermissionDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of RolePermission
	 * 
	 */
	default Long count(Request<RolePermissionDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from RolePermission e where e IS NOT NULL";
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
	default String getWhereExpression(Request<RolePermissionDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		RolePermissionDto dto = request.getData() != null ? request.getData() : new RolePermissionDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (RolePermissionDto elt : request.getDatas()) {
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
	default String generateCriteria(RolePermissionDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
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
			if (Utilities.notBlank(dto.getUpdatedAt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedAt", dto.getUpdatedAt(), "e.updatedAt", "Date", dto.getUpdatedAtParam(), param, index, locale));
			}
			if (dto.getUpdatedBy()!= null && dto.getUpdatedBy() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("updatedBy", dto.getUpdatedBy(), "e.updatedBy", "Integer", dto.getUpdatedByParam(), param, index, locale));
			}
			if (dto.getPermissionId()!= null && dto.getPermissionId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("permissionId", dto.getPermissionId(), "e.permission.id", "Integer", dto.getPermissionIdParam(), param, index, locale));
			}
			if (dto.getRoleId()!= null && dto.getRoleId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("roleId", dto.getRoleId(), "e.role.id", "Integer", dto.getRoleIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getPermissionName())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("permissionName", dto.getPermissionName(), "e.permission.name", "String", dto.getPermissionNameParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getRoleName())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("roleName", dto.getRoleName(), "e.role.name", "String", dto.getRoleNameParam(), param, index, locale));
			}
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
