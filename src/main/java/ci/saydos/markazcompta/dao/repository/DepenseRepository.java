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
import ci.saydos.markazcompta.dao.repository.customize._DepenseRepository;

/**
 * Repository : Depense.
 */
@Repository
public interface DepenseRepository extends JpaRepository<Depense, Integer>, _DepenseRepository {
	/**
	 * Finds Depense by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object Depense whose id is equals to the given id. If
	 *         no Depense is found, this method returns null.
	 */
	@Query("select e from Depense e where e.id = :id and e.isDeleted = :isDeleted")
	Depense findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Depense by using code as a search criteria.
	 * 
	 * @param code
	 * @return An Object Depense whose code is equals to the given code. If
	 *         no Depense is found, this method returns null.
	 */
	@Query("select e from Depense e where e.code = :code and e.isDeleted = :isDeleted")
	Depense findByCode(@Param("code")String code, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Depense by using montant as a search criteria.
	 * 
	 * @param montant
	 * @return An Object Depense whose montant is equals to the given montant. If
	 *         no Depense is found, this method returns null.
	 */
	@Query("select e from Depense e where e.montant = :montant and e.isDeleted = :isDeleted")
	List<Depense> findByMontant(@Param("montant")Double montant, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Depense by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object Depense whose createdAt is equals to the given createdAt. If
	 *         no Depense is found, this method returns null.
	 */
	@Query("select e from Depense e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<Depense> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Depense by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object Depense whose createdBy is equals to the given createdBy. If
	 *         no Depense is found, this method returns null.
	 */
	@Query("select e from Depense e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<Depense> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Depense by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object Depense whose isDeleted is equals to the given isDeleted. If
	 *         no Depense is found, this method returns null.
	 */
	@Query("select e from Depense e where e.isDeleted = :isDeleted")
	List<Depense> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Depense by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object Depense whose updatedAt is equals to the given updatedAt. If
	 *         no Depense is found, this method returns null.
	 */
	@Query("select e from Depense e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<Depense> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Depense by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object Depense whose updatedBy is equals to the given updatedBy. If
	 *         no Depense is found, this method returns null.
	 */
	@Query("select e from Depense e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<Depense> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Depense by using idDemande as a search criteria.
	 * 
	 * @param idDemande
	 * @return An Object Depense whose idDemande is equals to the given idDemande. If
	 *         no Depense is found, this method returns null.
	 */
	@Query("select e from Depense e where e.demande.id = :idDemande and e.isDeleted = :isDeleted")
	List<Depense> findByIdDemande(@Param("idDemande")Integer idDemande, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Depense by using idChargeFixe as a search criteria.
	 * 
	 * @param idChargeFixe
	 * @return An Object Depense whose idChargeFixe is equals to the given idChargeFixe. If
	 *         no Depense is found, this method returns null.
	 */
	@Query("select e from Depense e where e.utilisateur.id = :idChargeFixe and e.isDeleted = :isDeleted")
	List<Depense> findByIdChargeFixe(@Param("idChargeFixe")Integer idChargeFixe, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of Depense by using depenseDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of Depense
	 * @throws DataAccessException,ParseException
	 */
	default List<Depense> getByCriteria(Request<DepenseDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from Depense e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<Depense> query = em.createQuery(req, Depense.class);
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
	 * Finds count of Depense by using depenseDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of Depense
	 * 
	 */
	default Long count(Request<DepenseDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from Depense e where e IS NOT NULL";
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
	default String getWhereExpression(Request<DepenseDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		DepenseDto dto = request.getData() != null ? request.getData() : new DepenseDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (DepenseDto elt : request.getDatas()) {
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
	default String generateCriteria(DepenseDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("code", dto.getCode(), "e.code", "String", dto.getCodeParam(), param, index, locale));
			}
			if (dto.getMontant()!= null && dto.getMontant() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("montant", dto.getMontant(), "e.montant", "Double", dto.getMontantParam(), param, index, locale));
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
			if (dto.getIdDemande()!= null && dto.getIdDemande() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idDemande", dto.getIdDemande(), "e.demande.id", "Integer", dto.getIdDemandeParam(), param, index, locale));
			}
			if (dto.getIdChargeFixe()!= null && dto.getIdChargeFixe() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idChargeFixe", dto.getIdChargeFixe(), "e.utilisateur.id", "Integer", dto.getIdChargeFixeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getDemandeCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("demandeCode", dto.getDemandeCode(), "e.demande.code", "String", dto.getDemandeCodeParam(), param, index, locale));
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
