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
import ci.saydos.markazcompta.dao.repository.customize._StockRepository;

/**
 * Repository : Stock.
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Integer>, _StockRepository {
    /**
     * Finds Stock by using id as a search criteria.
     *
     * @param id
     * @return An Object Stock whose id is equals to the given id. If
     * no Stock is found, this method returns null.
     */
    @Query("select e from Stock e where e.id = :id and e.isDeleted = :isDeleted")
    Stock findOne(@Param("id") Integer id, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Stock by using intitule as a search criteria.
     *
     * @param intitule
     * @return An Object Stock whose intitule is equals to the given intitule. If
     * no Stock is found, this method returns null.
     */
    @Query("select e from Stock e where e.intitule = :intitule and e.isDeleted = :isDeleted")
    Stock findByIntitule(@Param("intitule") String intitule, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Stock by using quantite as a search criteria.
     *
     * @param quantite
     * @return An Object Stock whose quantite is equals to the given quantite. If
     * no Stock is found, this method returns null.
     */
    @Query("select e from Stock e where e.quantite = :quantite and e.isDeleted = :isDeleted")
    List<Stock> findByQuantite(@Param("quantite") Integer quantite, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Stock by using etat as a search criteria.
     *
     * @param etat
     * @return An Object Stock whose etat is equals to the given etat. If
     * no Stock is found, this method returns null.
     */
    @Query("select e from Stock e where e.etat = :etat and e.isDeleted = :isDeleted")
    List<Stock> findByEtat(@Param("etat") String etat, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Stock by using dateEntree as a search criteria.
     *
     * @param dateEntree
     * @return An Object Stock whose dateEntree is equals to the given dateEntree. If
     * no Stock is found, this method returns null.
     */
    @Query("select e from Stock e where e.dateEntree = :dateEntree and e.isDeleted = :isDeleted")
    List<Stock> findByDateEntree(@Param("dateEntree") Date dateEntree, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Stock by using dateSortie as a search criteria.
     *
     * @param dateSortie
     * @return An Object Stock whose dateSortie is equals to the given dateSortie. If
     * no Stock is found, this method returns null.
     */
    @Query("select e from Stock e where e.dateSortie = :dateSortie and e.isDeleted = :isDeleted")
    List<Stock> findByDateSortie(@Param("dateSortie") Date dateSortie, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Stock by using createdAt as a search criteria.
     *
     * @param createdAt
     * @return An Object Stock whose createdAt is equals to the given createdAt. If
     * no Stock is found, this method returns null.
     */
    @Query("select e from Stock e where e.createdAt = :createdAt and e.isDeleted = :isDeleted")
    List<Stock> findByCreatedAt(@Param("createdAt") Date createdAt, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Stock by using createdBy as a search criteria.
     *
     * @param createdBy
     * @return An Object Stock whose createdBy is equals to the given createdBy. If
     * no Stock is found, this method returns null.
     */
    @Query("select e from Stock e where e.createdBy = :createdBy and e.isDeleted = :isDeleted")
    List<Stock> findByCreatedBy(@Param("createdBy") Integer createdBy, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Stock by using isDeleted as a search criteria.
     *
     * @param isDeleted
     * @return An Object Stock whose isDeleted is equals to the given isDeleted. If
     * no Stock is found, this method returns null.
     */
    @Query("select e from Stock e where e.isDeleted = :isDeleted")
    List<Stock> findByIsDeleted(@Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Stock by using updatedAt as a search criteria.
     *
     * @param updatedAt
     * @return An Object Stock whose updatedAt is equals to the given updatedAt. If
     * no Stock is found, this method returns null.
     */
    @Query("select e from Stock e where e.updatedAt = :updatedAt and e.isDeleted = :isDeleted")
    List<Stock> findByUpdatedAt(@Param("updatedAt") Date updatedAt, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds Stock by using updatedBy as a search criteria.
     *
     * @param updatedBy
     * @return An Object Stock whose updatedBy is equals to the given updatedBy. If
     * no Stock is found, this method returns null.
     */
    @Query("select e from Stock e where e.updatedBy = :updatedBy and e.isDeleted = :isDeleted")
    List<Stock> findByUpdatedBy(@Param("updatedBy") Integer updatedBy, @Param("isDeleted") Boolean isDeleted);

    /**
     * Finds List of Stock by using stockDto as a search criteria.
     *
     * @param request, em
     * @return A List of Stock
     * @throws DataAccessException,ParseException
     */
    default List<Stock> getByCriteria(Request<StockDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
        String                            req   = "select e from Stock e where e IS NOT NULL";
        HashMap<String, java.lang.Object> param = new HashMap<String, java.lang.Object>();
        req += getWhereExpression(request, param, locale);
        req += " order by e.id desc";
        TypedQuery<Stock> query = em.createQuery(req, Stock.class);
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
     * Finds count of Stock by using stockDto as a search criteria.
     *
     * @param request, em
     * @return Number of Stock
     */
    default Long count(Request<StockDto> request, EntityManager em, Locale locale) throws DataAccessException, Exception {
        String                            req   = "select count(e.id) from Stock e where e IS NOT NULL";
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
    default String getWhereExpression(Request<StockDto> request, HashMap<String, java.lang.Object> param, Locale locale) throws Exception {
        // main query
        StockDto dto = request.getData() != null ? request.getData() : new StockDto();
        dto.setIsDeleted(false);
        String mainReq = generateCriteria(dto, param, 0, locale);
        // others query
        String othersReq = "";
        if (request.getDatas() != null && !request.getDatas().isEmpty()) {
            Integer index = 1;
            for (StockDto elt : request.getDatas()) {
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
    default String generateCriteria(StockDto dto, HashMap<String, java.lang.Object> param, Integer index, Locale locale) throws Exception {
        List<String> listOfQuery = new ArrayList<String>();
        if (dto != null) {
            if (dto.getId() != null && dto.getId() > 0) {
                listOfQuery.add(CriteriaUtils.generateCriteria("id", dto.getId(), "e.id", "Integer", dto.getIdParam(), param, index, locale));
            }
            if (Utilities.notBlank(dto.getIntitule())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("intitule", dto.getIntitule(), "e.intitule", "String", dto.getIntituleParam(), param, index, locale));
            }
            if (dto.getQuantite() != null && dto.getQuantite() > 0) {
                listOfQuery.add(CriteriaUtils.generateCriteria("quantite", dto.getQuantite(), "e.quantite", "Integer", dto.getQuantiteParam(), param, index, locale));
            }
            if (dto.getEtat() != null) {
                listOfQuery.add(CriteriaUtils.generateCriteria("etat", dto.getEtat(), "e.etat", "enum", dto.getEtatParam(), param, index, locale));
            }
            if (Utilities.notBlank(dto.getDateEntree())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("dateEntree", dto.getDateEntree(), "e.dateEntree", "Date", dto.getDateEntreeParam(), param, index, locale));
            }
            if (Utilities.notBlank(dto.getDateSortie())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("dateSortie", dto.getDateSortie(), "e.dateSortie", "Date", dto.getDateSortieParam(), param, index, locale));
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
            if (Utilities.notBlank(dto.getUpdatedAt())) {
                listOfQuery.add(CriteriaUtils.generateCriteria("updatedAt", dto.getUpdatedAt(), "e.updatedAt", "Date", dto.getUpdatedAtParam(), param, index, locale));
            }
            if (dto.getUpdatedBy() != null && dto.getUpdatedBy() > 0) {
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
