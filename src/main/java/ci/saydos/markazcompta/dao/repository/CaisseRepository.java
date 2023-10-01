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
import ci.saydos.markazcompta.dao.repository.customize._CaisseRepository;

/**
 * Repository : Caisse.
 */
@Repository
public interface CaisseRepository extends JpaRepository<Caisse, Integer>, _CaisseRepository {
    /**
     * Finds Caisse by using id as a search criteria.
     *
     * @param id
     * @return An Object Caisse whose id is equals to the given id. If
     * no Caisse is found, this method returns null.
     */
    @Query("select e from Caisse e where e.id = :id and e.isDeleted = :isDeleted")
    Caisse findOne(@Param("id") Integer id, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Caisse by using createdAt as a search criteria.
     *
     * @param createdAt
     * @return An Object Caisse whose createdAt is equals to the given createdAt. If
     * no Caisse is found, this method returns null.
     */
    @Query("select e from Caisse e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
    List<Caisse> findByCreatedAt(@Param("createdAt") Date createdAt, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Caisse by using createdBy as a search criteria.
     *
     * @param createdBy
     * @return An Object Caisse whose createdBy is equals to the given createdBy. If
     * no Caisse is found, this method returns null.
     */
    @Query("select e from Caisse e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
    List<Caisse> findByCreatedBy(@Param("createdBy") Integer createdBy, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Caisse by using isDeleted as a search criteria.
     *
     * @param isDeleted
     * @return An Object Caisse whose isDeleted is equals to the given isDeleted. If
     * no Caisse is found, this method returns null.
     */
    @Query("select e from Caisse e where e.isDeleted = :isDeleted")
    List<Caisse> findByIsDeleted(@Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Caisse by using montantDisponible as a search criteria.
     *
     * @param solde
     * @return An Object Caisse whose montantDisponible is equals to the given montantDisponible. If
     * no Caisse is found, this method returns null.
     */
    @Query("select e from Caisse e where e.solde = :solde and e.isDeleted = :isDeleted")
    List<Caisse> findByMontantDisponible(@Param("solde") Double solde, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Caisse by using montantEntre as a search criteria.
     *
     * @param montantEntre
     * @return An Object Caisse whose montantEntre is equals to the given montantEntre. If
     * no Caisse is found, this method returns null.
     */
    @Query("select e from Caisse e where e.montantEntre = :montantEntre and e.isDeleted = :isDeleted")
    List<Caisse> findByMontantEntre(@Param("montantEntre") Double montantEntre, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Caisse by using type as a search criteria.
     *
     * @param type
     * @return An Object Caisse whose type is equals to the given type. If
     * no Caisse is found, this method returns null.
     */
    @Query("select e from Caisse e where e.type = :type and e.isDeleted = :isDeleted")
    List<Caisse> findByType(@Param("type") String type, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Caisse by using updatedAt as a search criteria.
     *
     * @param updatedAt
     * @return An Object Caisse whose updatedAt is equals to the given updatedAt. If
     * no Caisse is found, this method returns null.
     */
    @Query("select e from Caisse e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
    List<Caisse> findByUpdatedAt(@Param("updatedAt") Date updatedAt, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Caisse by using updatedBy as a search criteria.
     *
     * @param updatedBy
     * @return An Object Caisse whose updatedBy is equals to the given updatedBy. If
     * no Caisse is found, this method returns null.
     */
    @Query("select e from Caisse e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
    List<Caisse> findByUpdatedBy(@Param("updatedBy") Integer updatedBy, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Caisse by using montantSortie as a search criteria.
     *
     * @param montantSortie
     * @return An Object Caisse whose montantSortie is equals to the given montantSortie. If
     * no Caisse is found, this method returns null.
     */
    @Query("select e from Caisse e where e.montantSortie = :montantSortie and e.isDeleted = :isDeleted")
    List<Caisse> findByMontantSortie(@Param("montantSortie") Double montantSortie, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Caisse by using idUtilisateur as a search criteria.
     *
     * @param idUtilisateur
     * @return An Object Caisse whose idUtilisateur is equals to the given idUtilisateur. If
     * no Caisse is found, this method returns null.
     */
    @Query("select e from Caisse e where e.utilisateur.id = :idUtilisateur and e.isDeleted = :isDeleted")
    List<Caisse> findByIdUtilisateur(@Param("idUtilisateur") Integer idUtilisateur, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Caisse by using idDepense as a search criteria.
     *
     * @param idDepense
     * @return An Object Caisse whose idDepense is equals to the given idDepense. If
     * no Caisse is found, this method returns null.
     */
    @Query("select e from Caisse e where e.depense.id = :idDepense and e.isDeleted = :isDeleted")
    List<Caisse> findByIdDepense(@Param("idDepense") Integer idDepense, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds List of Caisse by using caisseDto as a search criteria.
     *
     * @param request, em
     * @return A List of Caisse
     * @throws DataAccessException,ParseException
     */
    default List<Caisse> getByCriteria(Request<CaisseDto> request, EntityManager em, Locale locale) throws Exception {
        String                            req   = "select e from Caisse e where e IS NOT NULL";
        HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
        req += getWhereExpression(request, param, locale);
        req += " order by e.id desc";
        TypedQuery<Caisse> query = em.createQuery(req, Caisse.class);
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
     * Finds count of Caisse by using caisseDto as a search criteria.
     *
     * @param request, em
     * @return Number of Caisse
     */
    default Long count(Request<CaisseDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
        String                            req   = "select count(e.id) from Caisse e where e IS NOT NULL";
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
     *
     * @param request
     * @param param
     * @param locale
     * @return
     * @throws Exception
     */
    default String getWhereExpression(Request<CaisseDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
        // main query
        CaisseDto dto = request.getData() != null ? request.getData() : new CaisseDto();
        dto.setIsDeleted(false);
        String mainReq = generateCriteria(dto, param, 0, locale);
        // others query
        String othersReq = "";
        if (request.getDatas() != null && !request.getDatas().isEmpty()) {
            Integer index = 1;
            for (CaisseDto elt : request.getDatas()) {
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
     *
     * @param dto
     * @param param
     * @param index
     * @param locale
     * @return
     * @throws Exception
     */
    default String generateCriteria(CaisseDto dto, HashMap<String, java.lang.Object> param, Integer index, Locale locale) throws Exception {
        List<String> listOfQuery = new ArrayList<String>();
        if (dto != null) {
            if (dto.getId() != null && dto.getId() > 0) {
                listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
            }
            if (Utilities.notBlank(dto.getCreatedAt())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("createdAt", dto.getCreatedAt(), "e.createdAt", "Date", dto.getCreatedAtParam(), param, index, locale));
            }
            if (dto.getCreatedBy() != null && dto.getCreatedBy() > 0) {
                listOfQuery.add(CriteriaUtils.generateCriteria("createdBy", dto.getCreatedBy(), "e.createdBy", "Integer", dto.getCreatedByParam(), param, index, locale));
            }
            if (dto.getIsDeleted() != null) {
                listOfQuery.add(CriteriaUtils.generateCriteria("isDeleted", dto.getIsDeleted(), "e.isDeleted", "Boolean", dto.getIsDeletedParam(), param, index, locale));
            }
            if (dto.getSolde() != null && dto.getSolde() > 0) {
                listOfQuery.add(CriteriaUtils.generateCriteria("solde", dto.getSolde(), "e.solde", "Double", dto.getSoldeParam(), param, index, locale));
            }
            if (dto.getMontantEntre() != null && dto.getMontantEntre() > 0) {
                listOfQuery.add(CriteriaUtils.generateCriteria("montantEntre", dto.getMontantEntre(), "e.montantEntre", "Double", dto.getMontantEntreParam(), param, index, locale));
            }
            if (dto.getType() != null) {
                listOfQuery.add(CriteriaUtils.generateCriteria("type", dto.getType(), "e.type", "enum", dto.getTypeParam(), param, index, locale));
            }
            if (Utilities.notBlank(dto.getUpdatedAt())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("updatedAt", dto.getUpdatedAt(), "e.updatedAt", "Date", dto.getUpdatedAtParam(), param, index, locale));
            }
            if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
                listOfQuery.add(CriteriaUtils.generateCriteria("updatedBy", dto.getUpdatedBy(), "e.updatedBy", "Integer", dto.getUpdatedByParam(), param, index, locale));
            }
            if (dto.getMontantSortie() != null && dto.getMontantSortie() > 0) {
                listOfQuery.add(CriteriaUtils.generateCriteria("montantSortie", dto.getMontantSortie(), "e.montantSortie", "Double", dto.getMontantSortieParam(), param, index, locale));
            }
            if (dto.getIdUtilisateur() != null && dto.getIdUtilisateur() > 0) {
                listOfQuery.add(CriteriaUtils.generateCriteria("idUtilisateur", dto.getIdUtilisateur(), "e.utilisateur.id", "Integer", dto.getIdUtilisateurParam(), param, index, locale));
            }
            if (dto.getIdDepense() != null && dto.getIdDepense() > 0) {
                listOfQuery.add(CriteriaUtils.generateCriteria("idDepense", dto.getIdDepense(), "e.depense.id", "Integer", dto.getIdDepenseParam(), param, index, locale));
            }
            if (Utilities.notBlank(dto.getUtilisateurFirstName())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("utilisateurFirstName", dto.getUtilisateurFirstName(), "e.utilisateur.firstName", "String", dto.getUtilisateurFirstNameParam(), param, index, locale));
            }
            if (Utilities.notBlank(dto.getUtilisateurLastName())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("utilisateurLastName", dto.getUtilisateurLastName(), "e.utilisateur.lastName", "String", dto.getUtilisateurLastNameParam(), param, index, locale));
            }

            if (Utilities.notBlank(dto.getDepenseCode())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("depenseCode", dto.getDepenseCode(), "e.depense.code", "String", dto.getDepenseCodeParam(), param, index, locale));
            }
            List<String> listOfCustomQuery = _generateCriteria(dto, param, index, locale);
            if (Utilities.isNotEmpty(listOfCustomQuery)) {
                listOfQuery.addAll(listOfCustomQuery);
            }
        }
        return CriteriaUtils.getCriteriaByListOfQuery(listOfQuery);
    }
}
