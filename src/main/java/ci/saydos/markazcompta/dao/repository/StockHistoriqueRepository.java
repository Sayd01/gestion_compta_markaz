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
import ci.saydos.markazcompta.dao.repository.customize._StockHistoriqueRepository;

/**
 * Repository : StockHistorique.
 */
@Repository
public interface StockHistoriqueRepository extends JpaRepository<StockHistorique, Integer>, _StockHistoriqueRepository {
	/**
	 * Finds StockHistorique by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object StockHistorique whose id is equals to the given id. If
	 *         no StockHistorique is found, this method returns null.
	 */
	@Query("select e from StockHistorique e where e.id = :id and e.isDeleted = :isDeleted")
	StockHistorique findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds StockHistorique by using intitule as a search criteria.
	 * 
	 * @param intitule
	 * @return An Object StockHistorique whose intitule is equals to the given intitule. If
	 *         no StockHistorique is found, this method returns null.
	 */
	@Query("select e from StockHistorique e where e.intitule = :intitule and e.isDeleted = :isDeleted")
	StockHistorique findByIntitule(@Param("intitule")String intitule, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds StockHistorique by using quantite as a search criteria.
	 * 
	 * @param quantite
	 * @return An Object StockHistorique whose quantite is equals to the given quantite. If
	 *         no StockHistorique is found, this method returns null.
	 */
	@Query("select e from StockHistorique e where e.quantite = :quantite and e.isDeleted = :isDeleted")
	List<StockHistorique> findByQuantite(@Param("quantite")Integer quantite, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds StockHistorique by using etat as a search criteria.
	 * 
	 * @param etat
	 * @return An Object StockHistorique whose etat is equals to the given etat. If
	 *         no StockHistorique is found, this method returns null.
	 */
	@Query("select e from StockHistorique e where e.etat = :etat and e.isDeleted = :isDeleted")
	List<StockHistorique> findByEtat(@Param("etat")String etat, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds StockHistorique by using dateMvt as a search criteria.
	 * 
	 * @param dateMvt
	 * @return An Object StockHistorique whose dateMvt is equals to the given dateMvt. If
	 *         no StockHistorique is found, this method returns null.
	 */
	@Query("select e from StockHistorique e where e.dateMvt = :dateMvt and e.isDeleted = :isDeleted")
	List<StockHistorique> findByDateMvt(@Param("dateMvt")Date dateMvt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds StockHistorique by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object StockHistorique whose createdAt is equals to the given createdAt. If
	 *         no StockHistorique is found, this method returns null.
	 */
	@Query("select e from StockHistorique e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<StockHistorique> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds StockHistorique by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object StockHistorique whose createdBy is equals to the given createdBy. If
	 *         no StockHistorique is found, this method returns null.
	 */
	@Query("select e from StockHistorique e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<StockHistorique> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds StockHistorique by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object StockHistorique whose isDeleted is equals to the given isDeleted. If
	 *         no StockHistorique is found, this method returns null.
	 */
	@Query("select e from StockHistorique e where e.isDeleted = :isDeleted")
	List<StockHistorique> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds StockHistorique by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object StockHistorique whose updatedAt is equals to the given updatedAt. If
	 *         no StockHistorique is found, this method returns null.
	 */
	@Query("select e from StockHistorique e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<StockHistorique> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds StockHistorique by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object StockHistorique whose updatedBy is equals to the given updatedBy. If
	 *         no StockHistorique is found, this method returns null.
	 */
	@Query("select e from StockHistorique e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<StockHistorique> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds StockHistorique by using idStock as a search criteria.
	 * 
	 * @param idStock
	 * @return An Object StockHistorique whose idStock is equals to the given idStock. If
	 *         no StockHistorique is found, this method returns null.
	 */
	@Query("select e from StockHistorique e where e.stock.id = :idStock and e.isDeleted = :isDeleted")
	List<StockHistorique> findByIdStock(@Param("idStock")Integer idStock, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of StockHistorique by using stockHistoriqueDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of StockHistorique
	 * @throws DataAccessException,ParseException
	 */
	default List<StockHistorique> getByCriteria(Request<StockHistoriqueDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from StockHistorique e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<StockHistorique> query = em.createQuery(req, StockHistorique.class);
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
	 * Finds count of StockHistorique by using stockHistoriqueDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of StockHistorique
	 * 
	 */
	default Long count(Request<StockHistoriqueDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from StockHistorique e where e IS NOT NULL";
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
	default String getWhereExpression(Request<StockHistoriqueDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		StockHistoriqueDto dto = request.getData() != null ? request.getData() : new StockHistoriqueDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (StockHistoriqueDto elt : request.getDatas()) {
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
	default String generateCriteria(StockHistoriqueDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getIntitule())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("intitule", dto.getIntitule(), "e.intitule", "String", dto.getIntituleParam(), param, index, locale));
			}
			if (dto.getQuantite()!= null && dto.getQuantite() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("quantite", dto.getQuantite(), "e.quantite", "Integer", dto.getQuantiteParam(), param, index, locale));
			}
			if (dto.getEtat() != null) {
				listOfQuery.add(CriteriaUtils.generateCriteria("etat", dto.getEtat(), "e.etat", "enum", dto.getEtatParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getDateMvt())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("dateMvt", dto.getDateMvt(), "e.dateMvt", "Date", dto.getDateMvtParam(), param, index, locale));
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
			if (dto.getIdStock()!= null && dto.getIdStock() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idStock", dto.getIdStock(), "e.stock.id", "Integer", dto.getIdStockParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getStockIntitule())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("stockIntitule", dto.getStockIntitule(), "e.stock.intitule", "String", dto.getStockIntituleParam(), param, index, locale));
			}
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
