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
import ci.saydos.markazcompta.dao.repository.customize._RecetteRepository;

/**
 * Repository : Recette.
 */
@Repository
public interface RecetteRepository extends JpaRepository<Recette, Integer>, _RecetteRepository {
	/**
	 * Finds Recette by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object Recette whose id is equals to the given id. If
	 *         no Recette is found, this method returns null.
	 */
	@Query("select e from Recette e where e.id = :id and e.isDeleted = :isDeleted")
	Recette findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Recette by using source as a search criteria.
	 * 
	 * @param source
	 * @return An Object Recette whose source is equals to the given source. If
	 *         no Recette is found, this method returns null.
	 */
	@Query("select e from Recette e where e.source = :source and e.isDeleted = :isDeleted")
	List<Recette> findBySource(@Param("source")String source, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Recette by using montant as a search criteria.
	 * 
	 * @param montant
	 * @return An Object Recette whose montant is equals to the given montant. If
	 *         no Recette is found, this method returns null.
	 */
	@Query("select e from Recette e where e.montant = :montant and e.isDeleted = :isDeleted")
	List<Recette> findByMontant(@Param("montant")String montant, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Recette by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object Recette whose createdAt is equals to the given createdAt. If
	 *         no Recette is found, this method returns null.
	 */
	@Query("select e from Recette e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<Recette> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Recette by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object Recette whose createdBy is equals to the given createdBy. If
	 *         no Recette is found, this method returns null.
	 */
	@Query("select e from Recette e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<Recette> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Recette by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object Recette whose isDeleted is equals to the given isDeleted. If
	 *         no Recette is found, this method returns null.
	 */
	@Query("select e from Recette e where e.isDeleted = :isDeleted")
	List<Recette> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Recette by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object Recette whose updatedAt is equals to the given updatedAt. If
	 *         no Recette is found, this method returns null.
	 */
	@Query("select e from Recette e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<Recette> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Recette by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object Recette whose updatedBy is equals to the given updatedBy. If
	 *         no Recette is found, this method returns null.
	 */
	@Query("select e from Recette e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<Recette> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of Recette by using recetteDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of Recette
	 * @throws DataAccessException,ParseException
	 */
	default List<Recette> getByCriteria(Request<RecetteDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from Recette e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<Recette> query = em.createQuery(req, Recette.class);
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
	 * Finds count of Recette by using recetteDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of Recette
	 * 
	 */
	default Long count(Request<RecetteDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from Recette e where e IS NOT NULL";
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
	default String getWhereExpression(Request<RecetteDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		RecetteDto dto = request.getData() != null ? request.getData() : new RecetteDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (RecetteDto elt : request.getDatas()) {
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
	default String generateCriteria(RecetteDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getSource())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("source", dto.getSource(), "e.source", "String", dto.getSourceParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getMontant())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("montant", dto.getMontant(), "e.montant", "String", dto.getMontantParam(), param, index, locale));
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
