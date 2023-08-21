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
import ci.saydos.markazcompta.dao.repository.customize._DemandeRepository;

/**
 * Repository : Demande.
 */
@Repository
public interface DemandeRepository extends JpaRepository<Demande, Integer>, _DemandeRepository {
	/**
	 * Finds Demande by using id as a search criteria.
	 * 
	 * @param id
	 * @return An Object Demande whose id is equals to the given id. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.id = :id and e.isDeleted = :isDeleted")
	Demande findOne(@Param("id")Integer id, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Demande by using label as a search criteria.
	 * 
	 * @param label
	 * @return An Object Demande whose label is equals to the given label. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.label = :label and e.isDeleted = :isDeleted")
	List<Demande> findByLabel(@Param("label")String label, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using code as a search criteria.
	 * 
	 * @param code
	 * @return An Object Demande whose code is equals to the given code. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.code = :code and e.isDeleted = :isDeleted")
	Demande findByCode(@Param("code")String code, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using description as a search criteria.
	 * 
	 * @param description
	 * @return An Object Demande whose description is equals to the given description. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.description = :description and e.isDeleted = :isDeleted")
	List<Demande> findByDescription(@Param("description")String description, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using montant as a search criteria.
	 * 
	 * @param montant
	 * @return An Object Demande whose montant is equals to the given montant. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.montant = :montant and e.isDeleted = :isDeleted")
	List<Demande> findByMontant(@Param("montant")Double montant, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using imageUrl as a search criteria.
	 * 
	 * @param imageUrl
	 * @return An Object Demande whose imageUrl is equals to the given imageUrl. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.imageUrl = :imageUrl and e.isDeleted = :isDeleted")
	List<Demande> findByImageUrl(@Param("imageUrl")String imageUrl, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using dateDebut as a search criteria.
	 * 
	 * @param dateDebut
	 * @return An Object Demande whose dateDebut is equals to the given dateDebut. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.dateDebut = :dateDebut and e.isDeleted = :isDeleted")
	List<Demande> findByDateDebut(@Param("dateDebut")Date dateDebut, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using dateFin as a search criteria.
	 * 
	 * @param dateFin
	 * @return An Object Demande whose dateFin is equals to the given dateFin. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.dateFin = :dateFin and e.isDeleted = :isDeleted")
	List<Demande> findByDateFin(@Param("dateFin")Date dateFin, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using statut as a search criteria.
	 * 
	 * @param statut
	 * @return An Object Demande whose statut is equals to the given statut. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.statut = :statut and e.isDeleted = :isDeleted")
	List<Demande> findByStatut(@Param("statut")String statut, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using createdAt as a search criteria.
	 * 
	 * @param createdAt
	 * @return An Object Demande whose createdAt is equals to the given createdAt. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
	List<Demande> findByCreatedAt(@Param("createdAt")Date createdAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using createdBy as a search criteria.
	 * 
	 * @param createdBy
	 * @return An Object Demande whose createdBy is equals to the given createdBy. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
	List<Demande> findByCreatedBy(@Param("createdBy")Integer createdBy, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using isDeleted as a search criteria.
	 * 
	 * @param isDeleted
	 * @return An Object Demande whose isDeleted is equals to the given isDeleted. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.isDeleted = :isDeleted")
	List<Demande> findByIsDeleted(@Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using updatedAt as a search criteria.
	 * 
	 * @param updatedAt
	 * @return An Object Demande whose updatedAt is equals to the given updatedAt. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
	List<Demande> findByUpdatedAt(@Param("updatedAt")Date updatedAt, @Param("isDeleted")Boolean isDeleted);
	/**
	 * Finds Demande by using updatedBy as a search criteria.
	 * 
	 * @param updatedBy
	 * @return An Object Demande whose updatedBy is equals to the given updatedBy. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
	List<Demande> findByUpdatedBy(@Param("updatedBy")Integer updatedBy, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Demande by using idUtilisateur as a search criteria.
	 * 
	 * @param idUtilisateur
	 * @return An Object Demande whose idUtilisateur is equals to the given idUtilisateur. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.utilisateur.id = :idUtilisateur and e.isDeleted = :isDeleted")
	List<Demande> findByIdUtilisateur(@Param("idUtilisateur")Integer idUtilisateur, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds Demande by using idDirection as a search criteria.
	 * 
	 * @param idDirection
	 * @return An Object Demande whose idDirection is equals to the given idDirection. If
	 *         no Demande is found, this method returns null.
	 */
	@Query("select e from Demande e where e.direction.id = :idDirection and e.isDeleted = :isDeleted")
	List<Demande> findByIdDirection(@Param("idDirection")Integer idDirection, @Param("isDeleted")Boolean isDeleted);

	/**
	 * Finds List of Demande by using demandeDto as a search criteria.
	 * 
	 * @param request, em
	 * @return A List of Demande
	 * @throws DataAccessException,ParseException
	 */
	default List<Demande> getByCriteria(Request<DemandeDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
		String req = "select e from Demande e where e IS NOT NULL";
		HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
		req += getWhereExpression(request, param, locale);
		req += " order by e.id desc";
		TypedQuery<Demande> query = em.createQuery(req, Demande.class);
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
	 * Finds count of Demande by using demandeDto as a search criteria.
	 * 
	 * @param request, em
	 * @return Number of Demande
	 * 
	 */
	default Long count(Request<DemandeDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception  {
		String req = "select count(e.id) from Demande e where e IS NOT NULL";
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
	default String getWhereExpression(Request<DemandeDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
		// main query
		DemandeDto dto = request.getData() != null ? request.getData() : new DemandeDto();
		dto.setIsDeleted(false);
		String mainReq = generateCriteria(dto, param, 0, locale);
		// others query
		String othersReq = "";
		if (request.getDatas() != null && !request.getDatas().isEmpty()) {
			Integer index = 1;
			for (DemandeDto elt : request.getDatas()) {
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
	default String generateCriteria(DemandeDto dto, HashMap<String, java.lang.Object> param, Integer index,  Locale locale) throws Exception{
		List<String> listOfQuery = new ArrayList<String>();
		if (dto != null) {
			if (dto.getId()!= null && dto.getId() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getLabel())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("label", dto.getLabel(), "e.label", "String", dto.getLabelParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("code", dto.getCode(), "e.code", "String", dto.getCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getDescription())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("description", dto.getDescription(), "e.description", "String", dto.getDescriptionParam(), param, index, locale));
			}
			if (dto.getMontant()!= null && dto.getMontant() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("montant", dto.getMontant(), "e.montant", "Double", dto.getMontantParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getImageUrl())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("imageUrl", dto.getImageUrl(), "e.imageUrl", "String", dto.getImageUrlParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getDateDebut())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("dateDebut", dto.getDateDebut(), "e.dateDebut", "Date", dto.getDateDebutParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getDateFin())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("dateFin", dto.getDateFin(), "e.dateFin", "Date", dto.getDateFinParam(), param, index, locale));
			}
			if (dto.getStatut() !=null ) {
				listOfQuery.add(CriteriaUtils.generateCriteria("statut", dto.getStatut(), "e.statut", "enum", dto.getStatutParam(), param, index, locale));
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
			if (dto.getIdUtilisateur()!= null && dto.getIdUtilisateur() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idUtilisateur", dto.getIdUtilisateur(), "e.utilisateur.id", "Integer", dto.getIdUtilisateurParam(), param, index, locale));
			}
			if (dto.getIdDirection()!= null && dto.getIdDirection() > 0) {
				listOfQuery.add(CriteriaUtils.generateCriteria("idDirection", dto.getIdDirection(), "e.direction.id", "Integer", dto.getIdDirectionParam(), param, index, locale));
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
			if (Utilities.notBlank(dto.getDirectionCode())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("directionCode", dto.getDirectionCode(), "e.direction.code", "String", dto.getDirectionCodeParam(), param, index, locale));
			}
			if (Utilities.notBlank(dto.getDirectionIntitule())) {
				listOfQuery.add(CriteriaUtils.generateCriteria("directionIntitule", dto.getDirectionIntitule(), "e.direction.intitule", "String", dto.getDirectionIntituleParam(), param, index, locale));
			}
			List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
			if (Utilities.isNotEmpty(listOfCustomQuery)) {
				listOfQuery.addAll(listOfCustomQuery);
			}
		}
		return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
	}
}
