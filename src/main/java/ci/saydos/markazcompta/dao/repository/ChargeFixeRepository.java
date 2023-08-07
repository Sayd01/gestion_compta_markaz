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
import ci.saydos.markazcompta.dao.repository.customize._ChargeFixeRepository;

/**
 * Repository : ChargeFixe.
 */
@Repository
public interface ChargeFixeRepository extends JpaRepository<ChargeFixe, Integer>, _ChargeFixeRepository {
	/**
	 * Finds ChargeFixe by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object ChargeFixe whose id is equals to the given id. If
	 *         no ChargeFixe is found, this method returns null.
	 */
	@Query("select e from ChargeFixe e where e.id = :id and e.isDeleted = :isDeleted")
	ChargeFixe findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds ChargeFixe by using code as a search criteria.
	 * 
	 * @param code
	 * @return An Object ChargeFixe whose code is equals to the given code. If
	 *         no ChargeFixe is found, this method returns null.
	 */
	@Query("select e from ChargeFixe e where e.code = :code and e.isDeleted = :isDeleted")
	ChargeFixe findByCode(@Param("code")String code, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ChargeFixe by using label as a search criteria.
	 * 
	 * @param label
	 * @return An Object ChargeFixe whose label is equals to the given label. If
	 *         no ChargeFixe is found, this method returns null.
	 */
	@Query("select e from ChargeFixe e where e.label = :label and e.isDeleted = :isDeleted")
	List<ChargeFixe> findByLabel(@Param("label")String label, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ChargeFixe by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object ChargeFixe whose createdAt is equals to the given createdAt. If
	 *         no ChargeFixe is found, this method returns null.
	 */
	@Query("select e from ChargeFixe e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<ChargeFixe> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ChargeFixe by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object ChargeFixe whose createdBy is equals to the given createdBy. If
	 *         no ChargeFixe is found, this method returns null.
	 */
	@Query("select e from ChargeFixe e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<ChargeFixe> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ChargeFixe by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object ChargeFixe whose isDeleted is equals to the given isDeleted. If
	 *         no ChargeFixe is found, this method returns null.
	 */
	@Query("select e from ChargeFixe e where e.isDeleted = :isDeleted")
	List<ChargeFixe> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ChargeFixe by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object ChargeFixe whose updatedAt is equals to the given updatedAt. If
	 *         no ChargeFixe is found, this method returns null.
	 */
	@Query("select e from ChargeFixe e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<ChargeFixe> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds ChargeFixe by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object ChargeFixe whose updatedBy is equals to the given updatedBy. If
	 *         no ChargeFixe is found, this method returns null.
	 */
	@Query("select e from ChargeFixe e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<ChargeFixe> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of ChargeFixe by using chargeFixeDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of ChargeFixe
	 * @throws DataAccessException,ParseException
	 */
	default List<ChargeFixe> getByCriteria(Request<ChargeFixeDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from ChargeFixe e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<ChargeFixe> query = em.createQuery(req, ChargeFixe.class);
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
	 * Finds count of ChargeFixe by using chargeFixeDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of ChargeFixe
	 * 
	 */
	default Long count(Request<ChargeFixeDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from ChargeFixe e where e IS NOT NULL";
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
	default String getWhereExpression(Request<ChargeFixeDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		ChargeFixeDto dto = request.getData() != null ? request.getData() : new ChargeFixeDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (ChargeFixeDto elt : request.getDatas()) {
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
	default String generateCriteria(ChargeFixeDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("code", dto.getCode(), "e.code", "String", dto.getCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLabel())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("label", dto.getLabel(), "e.label", "String", dto.getLabelParam(), param, index, locale));
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
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
