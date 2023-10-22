package ci.saydos.markazcompta.business;

import ci.saydos.markazcompta.utils.*;
import ci.saydos.markazcompta.utils.contract.Request;
import ci.saydos.markazcompta.utils.contract.Response;
import ci.saydos.markazcompta.utils.dto.*;
import ci.saydos.markazcompta.utils.enums.StatutDemandeEnum;
import ci.saydos.markazcompta.utils.exception.FunctionalException;
import ci.saydos.markazcompta.utils.exception.InvalidEntityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

@Log
@Service
@RequiredArgsConstructor
public class StatistiqueBusiness {
    private final JdbcTemplate    mysqlTemplate;
    private final FunctionalError functionalError;
    private final String          formatDate     = "yyyy-MM-dd";
    private final String          formatDateTime = "yyyy-MM-dd HH:mm:ss";
    private static final Logger logger = LoggerFactory.getLogger(StatistiqueBusiness.class);


    public Response<Map<String, Object>> demande(Request<DemandeHistoriqueDto> request) throws ParseException {
        logger.info("La méthode demande a été appelée.");

        Response<Map<String, Object>> response = new Response<>();
        DemandeHistoriqueDto dto = request.getData();
        String dateDebut = Utilities.formatDate(dto.getCreatedAtParam().getStart(), formatDateTime);
        String dateFin = Utilities.formatDate(dto.getCreatedAtParam().getEnd(), formatDateTime);
        List<Map<String, Object>> rows = new ArrayList<>();
        Map<String, Object> countMap = new HashMap<>();

        logger.debug("Dates de début et de fin : {} - {}", dateDebut, dateFin);

        for (StatutDemandeEnum statut : StatutDemandeEnum.values()) {
            countMap.put(statut.toString().toLowerCase(), 0L);
        }

        if (Utilities.notBlank(dateDebut) && Utilities.notBlank(dateFin)) {
            logger.debug("Exécution de la requête SQL pour la plage de dates : {} - {}", dateDebut, dateFin);

            String sql = "SELECT d.statut, COUNT(*) AS nombre_total_demandes_par_type " +
                    "FROM demande d " +
                    "WHERE d.created_at BETWEEN ? AND ? " +
                    "GROUP BY d.statut";

            Timestamp timestampDebut = Utilities.convertStringToTimestamp(dateDebut);
            Timestamp timestampFin = Utilities.convertStringToTimestamp(dateFin);
            rows = mysqlTemplate.queryForList(sql, timestampDebut, timestampFin);

            Long totalDemandes = getTotalDemandesByDateRange(dateDebut, dateFin);
            countMap.put("totalDemandes", totalDemandes);

            logger.debug("Requête SQL exécutée avec succès.");
        } else {
            logger.error("Les dates de début et/ou de fin sont vides.");
            throw new InvalidEntityException(functionalError.FIELD_EMPTY("start et/ou end", Locale.FRANCE));
        }

        if (Utilities.isNotEmpty(rows)) {
            for (Map<String, Object> row : rows) {
                String statut = (String) row.get("statut");
                Long count = (Long) row.get("nombre_total_demandes_par_type");
                countMap.put(statut.toLowerCase(), count);
            }
        } else {
            logger.warn("Aucune donnée trouvée pour les dates fournies.");
            throw new FunctionalException(functionalError.DATA_EMPTY("", Locale.FRANCE));
        }

        Status status = new Status();
        status.setCode(StatusCode.SUCCESS);
        status.setMessage(StatusMessage.SUCCESS);
        response.setHttpCode(HttpStatus.OK.value());
        response.setItem(countMap);
        response.setStatus(status);
        response.setHasError(false);

        logger.info("La méthode demande s'est terminée avec succès.");

        return response;
    }

    public Response<Map<String, Object>> caisse(Request<DemandeHistoriqueDto> request) throws ParseException {
        Response<Map<String, Object>> response  = new Response<>();
        DemandeHistoriqueDto          dto       = request.getData();
        String                        dateDebut = Utilities.formatDate(dto.getCreatedAtParam().getStart(), formatDateTime);
        String                        dateFin   = Utilities.formatDate(dto.getCreatedAtParam().getEnd(), formatDateTime);

        List<Map<String, Object>> rows     = new ArrayList<>();
        Map<String, Object>       countMap = new HashMap<>();

        if (Utilities.notBlank(dateDebut) && Utilities.notBlank(dateFin)) {
//            Double montantTotal       = getMontantDisponibleByDateRange(dateDebut, dateFin) != null ? getMontantDisponibleByDateRange(dateDebut, dateFin) : 0.0;
            Double montantTotalSortie = getTotalMontantsSortieByDateRange(dateDebut, dateFin) != null ? getTotalMontantsSortieByDateRange(dateDebut, dateFin) : 0.0;
            Double montantTotalEntre  = getTotalMontantsEntresByDateRange(dateDebut, dateFin) != null ? getTotalMontantsEntresByDateRange(dateDebut, dateFin) : 0.0;

            Double montantTotal = montantTotalEntre - montantTotalSortie;

            System.out.println(montantTotalEntre);
            countMap.put("montantTotal", montantTotal);
            countMap.put("montantTotalSortie", montantTotalSortie);
            countMap.put("montantTotalEntre", montantTotalEntre);
        } else {
            throw new InvalidEntityException(functionalError.FIELD_EMPTY("start et/ou end", Locale.FRANCE));
        }

        response.setItem(countMap);
        Status status = new Status();
        status.setCode(StatusCode.SUCCESS);
        status.setMessage(StatusMessage.SUCCESS);
        response.setHttpCode(HttpStatus.OK.value());
        response.setItem(countMap);
        response.setStatus(status);
        response.setHasError(false);
        return response;
    }

    public Long getTotalDemandesByDateRange(String dateDebut, String dateFin) throws ParseException {
        String sql = "SELECT COUNT(*) AS nombre_total_demandes " +
                "FROM demande d " +
                "WHERE d.created_at BETWEEN ? AND ?";

        Timestamp timestampDebut = Utilities.convertStringToTimestamp(dateDebut);
        Timestamp timestampFin   = Utilities.convertStringToTimestamp(dateFin);

        Long result = mysqlTemplate.queryForObject(sql, Long.class, timestampDebut, timestampFin);

        if (Utilities.blank(String.valueOf(result))) {
            throw new FunctionalException(functionalError.DATA_EMPTY("-", Locale.FRANCE));
        }
        return result;
    }

    public Double getMontantDisponibleByDateRange(String dateDebut, String dateFin) throws ParseException {
        String sql = "SELECT SUM(CASE WHEN created_at BETWEEN ? AND ? THEN montant_entre - montant_sortie ELSE 0 END) AS montant_disponible FROM caisse";

        Timestamp timestampDebut = Utilities.convertStringToTimestamp(dateDebut);
        Timestamp timestampFin   = Utilities.convertStringToTimestamp(dateFin);

        Double result = mysqlTemplate.queryForObject(sql, Double.class, timestampDebut, timestampFin);

        if (Utilities.blank(String.valueOf(result))) {
            throw new FunctionalException(functionalError.DATA_EMPTY("-", Locale.FRANCE));
        }
        return result;
    }

    public Double getTotalMontantsEntresByDateRange(String dateDebut, String dateFin) throws ParseException {
        String sql = "SELECT SUM(montant_entre) AS total_montants_entres FROM caisse WHERE created_at BETWEEN ? AND ?";

        Timestamp timestampDebut = Utilities.convertStringToTimestamp(dateDebut);
        Timestamp timestampFin   = Utilities.convertStringToTimestamp(dateFin);

        Double result = mysqlTemplate.queryForObject(sql, Double.class, timestampDebut, timestampFin);

        if (Utilities.blank(String.valueOf(result))) {
            throw new FunctionalException(functionalError.DATA_EMPTY("-", Locale.FRANCE));
        }
        return result;
    }

    public Double getTotalMontantsSortieByDateRange(String dateDebut, String dateFin) throws ParseException {
        String sql = "SELECT SUM(montant_sortie) AS total_montants_entres FROM caisse WHERE created_at BETWEEN ? AND ?";

        Timestamp timestampDebut = Utilities.convertStringToTimestamp(dateDebut);
        Timestamp timestampFin   = Utilities.convertStringToTimestamp(dateFin);

        Double result = mysqlTemplate.queryForObject(sql, Double.class, timestampDebut, timestampFin);

        if (Utilities.blank(String.valueOf(result))) {
            throw new FunctionalException(functionalError.DATA_EMPTY("-", Locale.FRANCE));
        }
        return result;
    }

    public Response<StatDemandeDto> getStatistiquesParMois(Request<DemandeHistoriqueDto> request) {
        DemandeHistoriqueDto     dto         = request.getData();
        String                   dateDebut   = dto.getCreatedAtParam().getStart();
        String                   dateFin     = dto.getCreatedAtParam().getEnd();
        String                   granularite = dto.getGranularite();
        Response<StatDemandeDto> response    = new Response<>();

        if (Utilities.blank(dateDebut) || Utilities.blank(dateFin) || Utilities.blank(granularite)) {
            throw new InvalidEntityException(functionalError.FIELD_EMPTY("dateDebut et/ou dateFin et/ou granularite", Locale.FRANCE));
        }

        String                    sql  = "";
        List<Map<String, Object>> rows = new ArrayList<>();
        if ("jour".equalsIgnoreCase(granularite)) {
            sql = "WITH RECURSIVE Days AS (\n" +
                    "  SELECT\n" +
                    "    DATE_FORMAT(?, '%Y-%m-%d') AS day_start,\n" +
                    "    DATE_FORMAT(?, '%Y-%m-%d') AS day_end,\n" +
                    "    DATE_FORMAT(?, '%Y-%m-%d') AS day\n" +
                    "  UNION ALL\n" +
                    "  SELECT\n" +
                    "    DATE_ADD(day_start, INTERVAL 1 DAY) AS day_start,\n" +
                    "    day_end,\n" +
                    "    DATE_FORMAT(DATE_ADD(day_start, INTERVAL 1 DAY), '%Y-%m-%d') AS day\n" +
                    "  FROM\n" +
                    "    Days\n" +
                    "  WHERE\n" +
                    "    DATE_ADD(day_start, INTERVAL 1 DAY) <= day_end\n" +
                    ")\n" +
                    "\n" +
                    "SELECT\n" +
                    "  CONCAT(YEAR(Days.day), '-', LPAD(MONTH(Days.day), 2, '0'), '-', LPAD(DAY(Days.day), 2, '0')) AS date,\n" +
                    "  S.statut,\n" +
                    "  COALESCE(COUNT(DH.id), 0) AS nombre_demandes\n" +
                    "FROM\n" +
                    "  Days\n" +
                    "CROSS JOIN\n" +
                    "  (SELECT DISTINCT statut FROM demande) S\n" +
                    "LEFT JOIN\n" +
                    "  demande DH ON YEAR(DH.created_at) = YEAR(Days.day) AND MONTH(DH.created_at) = MONTH(Days.day) AND DAY(DH.created_at) = DAY(Days.day) AND DH.statut = S.statut AND DH.is_deleted = false\n" +
                    "GROUP BY\n" +
                    "  date, S.statut\n" +
                    "ORDER BY\n" +
                    "  date, S.statut;\n";

            rows = mysqlTemplate.queryForList(sql, dateDebut, dateFin, dateDebut);
            List<StatDemandeDto> statDemandeDtos = new ArrayList<>();
            System.out.println(rows);

            // Créez une liste de tous les statuts possibles
            List<String> statutsPossibles = Arrays.asList("initie", "valide", "invalide", "termine");

            // Créez une structure de données pour stocker les statistiques par date et statut
            Map<String, Map<String, Long>> statistiquesParDate = new HashMap<>();

            for (Map<String, Object> row : rows) {
                String date           = (String) row.get("date");
                String statut         = row.containsKey("statut") ? (String) row.get("statut") : "zero";
                Long   nombreDemandes = (Long) row.get("nombre_demandes");

                // Vérifiez si la date existe dans la structure de données
                if (!statistiquesParDate.containsKey(date)) {
                    statistiquesParDate.put(date, new HashMap<>());
                }

                // Stockez la valeur du nombre de demandes pour le statut correspondant
                statistiquesParDate.get(date).put(statut.toLowerCase(), nombreDemandes);
            }

            // Créez un StatDemandeDto pour chaque date
            for (String date : statistiquesParDate.keySet()) {
                StatDemandeDto statDemandeDto = new StatDemandeDto();
                statDemandeDto.setDate(date);

                List<StatutDto> statutDtos = new ArrayList<>();

                for (String statut : statutsPossibles) {
                    Long      valeur    = statistiquesParDate.get(date).getOrDefault(statut, 0L);
                    StatutDto statutDto = new StatutDto();
                    statutDto.setKey(statut);
                    statutDto.setValue(Double.valueOf(valeur));
                    statutDtos.add(statutDto);
                }

                statDemandeDto.setDatas(statutDtos);
                statDemandeDtos.add(statDemandeDto);
            }

            Comparator<StatDemandeDto> dateComparator = Comparator.comparing(StatDemandeDto::getDate);
            statDemandeDtos.sort(dateComparator);
            response.setItems(statDemandeDtos);

        } else if ("mois".equalsIgnoreCase(granularite)) {
            sql = "\n" +
                    "WITH Months AS (\n" +
                    "  SELECT DATE_ADD(?, INTERVAL n MONTH) AS start_month,\n" +
                    "         DATE_ADD(?, INTERVAL n + 1 MONTH) AS end_month\n" +
                    "  FROM (\n" +
                    "    SELECT t0 + t1 * 10 AS n\n" +
                    "    FROM\n" +
                    "      (SELECT 0 AS t0 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) AS t0,\n" +
                    "      (SELECT 0 AS t1 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) AS t1\n" +
                    "  ) AS Numbers\n" +
                    "  WHERE DATE_ADD(?, INTERVAL n MONTH) <= ?\n" +
                    ")\n" +
                    "\n" +
                    "SELECT\n" +
                    "    DATE_FORMAT(Months.start_month, '%Y-%m') AS date,\n" +
                    "    statuts.statut AS statut,\n" +
                    "    COALESCE(COUNT(dh.id), 0) AS nombre_demandes\n" +
                    "FROM\n" +
                    "    Months\n" +
                    "CROSS JOIN\n" +
                    "    (SELECT DISTINCT statut FROM demande) AS statuts\n" +
                    "LEFT JOIN\n" +
                    "    demande AS dh\n" +
                    "ON\n" +
                    "    DATE(dh.created_at) >= Months.start_month\n" +
                    "    AND DATE(dh.created_at) < Months.end_month\n" +
                    "    AND statuts.statut = dh.statut " +
                    "    AND dh.is_deleted = false\n" +
                    "GROUP BY\n" +
                    "    date, statuts.statut\n" +
                    "ORDER BY\n" +
                    "    date, statuts.statut;\n";

            rows = mysqlTemplate.queryForList(sql, dateDebut, dateDebut, dateDebut, dateFin);
            List<StatDemandeDto> statDemandeDtos = new ArrayList<>();
            System.out.println(rows);

            // Créez une liste de tous les statuts possibles
            List<String> statutsPossibles = Arrays.asList("initie", "valide", "invalide", "termine");

            // Créez une structure de données pour stocker les statistiques par date et statut
            Map<String, Map<String, Long>> statistiquesParDate = new HashMap<>();
            if (!Utilities.isNotEmpty(rows)){
                throw new FunctionalException(functionalError.DATA_EMPTY("", Locale.FRANCE));
            }
            for (Map<String, Object> row : rows) {
                String date           = (String) row.get("date");
                String statut         = row.containsKey("statut") ? (String) row.get("statut") : "zero";
                Long   nombreDemandes = (Long) row.get("nombre_demandes");

                // Vérifiez si la date existe dans la structure de données
                if (!statistiquesParDate.containsKey(date)) {
                    statistiquesParDate.put(date, new HashMap<>());
                }

                // Stockez la valeur du nombre de demandes pour le statut correspondant
                statistiquesParDate.get(date).put(statut.toLowerCase(), nombreDemandes);
            }

            // Créez un StatDemandeDto pour chaque date
            for (String date : statistiquesParDate.keySet()) {
                StatDemandeDto statDemandeDto = new StatDemandeDto();
                statDemandeDto.setDate(date);

                List<StatutDto> statutDtos = new ArrayList<>();

                for (String statut : statutsPossibles) {
                    Long      valeur    = statistiquesParDate.get(date).getOrDefault(statut, 0L);
                    StatutDto statutDto = new StatutDto();
                    statutDto.setKey(statut);
                    statutDto.setValue(Double.valueOf(valeur));
                    statutDtos.add(statutDto);
                }

                statDemandeDto.setDatas(statutDtos);
                statDemandeDtos.add(statDemandeDto);
            }

            Comparator<StatDemandeDto> dateComparator = Comparator.comparing(StatDemandeDto::getDate);
            statDemandeDtos.sort(dateComparator);
            response.setItems(statDemandeDtos);


        } else if ("annee".equalsIgnoreCase(granularite)) {
            // Requête SQL pour obtenir des statistiques par année
            sql = "SELECT\n" +
                    "    DATE_FORMAT(d.date, '%Y') AS annee,\n" +
                    "    s.statut,\n" +
                    "    COALESCE(COUNT(dh.id), 0) AS nombre_demandes\n" +
                    "FROM\n" +
                    "    (\n" +
                    "        SELECT DISTINCT DATE(created_at) AS date FROM demande_historique\n" +
                    "        WHERE created_at BETWEEN ? AND ?\n" +
                    "    ) d\n" +
                    "CROSS JOIN\n" +
                    "    (\n" +
                    "        SELECT DISTINCT statut FROM demande\n" +
                    "    ) s\n" +
                    "LEFT JOIN\n" +
                    "    demande dh\n" +
                    "ON\n" +
                    "    d.date = DATE(dh.created_at) AND s.statut = dh.statut AND dh.is_deleted = false\n" +
                    "GROUP BY\n" +
                    "    annee, s.statut\n" +
                    "ORDER BY\n" +
                    "    annee, s.statut;\n";

            rows = mysqlTemplate.queryForList(sql, dateDebut, dateFin);
            if (!Utilities.isNotEmpty(rows)){
                throw new FunctionalException(functionalError.DATA_EMPTY("", Locale.FRANCE));
            }
            List<StatDemandeDto> statDemandeDtos = new ArrayList<>();
            System.out.println(rows);

            // Créez une liste de tous les statuts possibles
            List<String> statutsPossibles = Arrays.asList("initie", "valide", "invalide", "termine");

            // Créez une structure de données pour stocker les statistiques par date et statut
            Map<String, Map<String, Long>> statistiquesParDate = new HashMap<>();

            for (Map<String, Object> row : rows) {
                String date           = (String) row.get("date");
                String statut         = row.containsKey("statut") ? (String) row.get("statut") : "zero";
                Long   nombreDemandes = (Long) row.get("nombre_demandes");

                // Vérifiez si la date existe dans la structure de données
                if (!statistiquesParDate.containsKey(date)) {
                    statistiquesParDate.put(date, new HashMap<>());
                }

                // Stockez la valeur du nombre de demandes pour le statut correspondant
                statistiquesParDate.get(date).put(statut.toLowerCase(), nombreDemandes);
            }

            // Créez un StatDemandeDto pour chaque date
            for (String date : statistiquesParDate.keySet()) {
                StatDemandeDto statDemandeDto = new StatDemandeDto();
                statDemandeDto.setDate(date);

                List<StatutDto> statutDtos = new ArrayList<>();

                for (String statut : statutsPossibles) {
                    Long      valeur    = statistiquesParDate.get(date).getOrDefault(statut, 0L);
                    StatutDto statutDto = new StatutDto();
                    statutDto.setKey(statut);
                    statutDto.setValue(Double.valueOf(valeur));
                    statutDtos.add(statutDto);
                }

                statDemandeDto.setDatas(statutDtos);
                statDemandeDtos.add(statDemandeDto);
            }

            Comparator<StatDemandeDto> dateComparator = Comparator.comparing(StatDemandeDto::getDate);
            statDemandeDtos.sort(dateComparator);
            response.setItems(statDemandeDtos);

        }

        Status status = new Status();
        status.setCode(StatusCode.SUCCESS);
        status.setMessage(StatusMessage.SUCCESS);
        response.setHttpCode(HttpStatus.OK.value());
        response.setStatus(status);
        response.setHasError(false);
        return response;
    }

    /**
     *
     */
    public Response<StatCaisseDto> getStatistiquesCaisseParPeriode(Request<CaisseDto> request) throws ParseException {
        CaisseDto               dto         = request.getData();
        String                  granularite = dto.getGranularite();
        String                  dateDebut   = Utilities.formatDate(dto.getCreatedAtParam().getStart(), formatDate);
        String                  dateFin     = Utilities.formatDate(dto.getCreatedAtParam().getEnd(), formatDate);
        Response<StatCaisseDto> response    = new Response<>();

        if (Utilities.blank(dateDebut) || Utilities.blank(dateFin)) {
            throw new InvalidEntityException(functionalError.FIELD_EMPTY("dateDebut et/ou dateFin et/ou granularite", Locale.FRANCE));
        }

        String                    sql = "";
        List<Map<String, Object>> rows;

        if ("jour".equalsIgnoreCase(granularite)) {
            sql = "SELECT date_range.date AS date,\n" +
                    "       COALESCE(SUM(CASE WHEN c.type = 'ENTREE' THEN c.montant_entre ELSE 0 END), 0) AS total_entrees,\n" +
                    "       COALESCE(SUM(CASE WHEN c.type = 'SORTIE' THEN c.montant_sortie ELSE 0 END), 0) AS total_sorties\n" +
                    "FROM(\n" +
                    "    SELECT DATE_ADD(?, INTERVAL n DAY) AS date\n" +
                    "    FROM (\n" +
                    "            SELECT t0 + t1 * 10 + t2 * 100 + t3 * 1000 AS n\n" +
                    "            FROM (\n" +
                    "                    SELECT 0 AS t0 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9\n" +
                    "                ) AS t0,\n" +
                    "                (\n" +
                    "                    SELECT 0 AS t1 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9\n" +
                    "                ) AS t1,\n" +
                    "                (\n" +
                    "                    SELECT 0 AS t2 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9\n" +
                    "                ) AS t2,\n" +
                    "                    (\n" +
                    "                        SELECT 0 AS t3 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9\n" +
                    "                    ) AS t3\n" +
                    "        ) AS Numbers\n" +
                    "            WHERE DATE_ADD(?, INTERVAL n DAY) BETWEEN ? AND ?\n" +
                    "    ) AS date_range\n" +
                    "LEFT JOIN\n" +
                    "    caisse c ON DATE(c.created_at) = date_range.date AND c.is_deleted = false\n" +
                    "GROUP BY date_range.date;";

            rows = mysqlTemplate.queryForList(sql, dateDebut, dateDebut, dateDebut, dateFin);

            if (!Utilities.isNotEmpty(rows)){
                throw new FunctionalException(functionalError.DATA_EMPTY("", Locale.FRANCE));
            }
            Map<String, StatCaisseDto> resultatGroupeParDate = new HashMap<>();

            for (Map<String, Object> row : rows) {
                String date         = (String) row.get("date");
                Double totalEntrees = (Double) row.get("total_entrees");
                Double totalSortie  = (Double) row.get("total_sorties");

                // Si la date n'existe pas encore dans la structure de données, créez-la
                if (!resultatGroupeParDate.containsKey(date)) {
                    StatCaisseDto statCaisseDto = new StatCaisseDto();
                    statCaisseDto.setDate(date);
                    statCaisseDto.setDatas(new ArrayList<>());
                    resultatGroupeParDate.put(date, statCaisseDto);
                }

                StatCaisseDto statCaisseDto = resultatGroupeParDate.get(date);
                StatutDto     debit         = new StatutDto();
                debit.setKey("montantTotalEntre");
                debit.setValue(totalEntrees);
                statCaisseDto.getDatas().add(debit);
                StatutDto credit = new StatutDto();
                credit.setKey("montantTotalSortie");
                credit.setValue(totalSortie);
                statCaisseDto.getDatas().add(credit);
            }

            List<StatCaisseDto>       caisseDtos     = new ArrayList<>(resultatGroupeParDate.values());
            Comparator<StatCaisseDto> dateComparator = Comparator.comparing(StatCaisseDto::getDate);
            caisseDtos.sort(dateComparator);
            response.setItems(caisseDtos);


        } else if ("mois".equalsIgnoreCase(granularite)) {
            sql = "SELECT DATE_FORMAT(date_range.date, '%Y-%m') AS date,\n" +
                    "                    COALESCE(SUM(CASE WHEN c.type = 'ENTREE' THEN c.montant_entre ELSE 0 END), 0) AS total_entrees,\n" +
                    "                    COALESCE(SUM(CASE WHEN c.type = 'SORTIE' THEN c.montant_sortie ELSE 0 END), 0) AS total_sorties\n" +
                    "                FROM\n" +
                    "                    (\n" +
                    "                     SELECT DATE_ADD(?, INTERVAL n MONTH) AS date\n" +
                    "                     FROM (\n" +
                    "                       SELECT t0 + t1 * 10 + t2 * 100 AS n\n" +
                    "                       FROM (\n" +
                    "                         SELECT 0 AS t0 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9\n" +
                    "                       ) AS t0,\n" +
                    "                       (\n" +
                    "                         SELECT 0 AS t1 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9\n" +
                    "                       ) AS t1,\n" +
                    "                       (\n" +
                    "                         SELECT 0 AS t2 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9\n" +
                    "                       ) AS t2\n" +
                    "                     ) AS Numbers\n" +
                    "                     WHERE DATE_ADD(?, INTERVAL n MONTH) BETWEEN ? AND ?\n" +
                    "                    ) AS date_range\n" +
                    "                LEFT JOIN\n" +
                    "                    caisse c ON DATE_FORMAT(c.created_at, '%Y-%m') = DATE_FORMAT(date_range.date, '%Y-%m') AND c.is_deleted = false\n" +
                    "                GROUP BY\n" +
                    "                    date_range.date;";

            rows = mysqlTemplate.queryForList(sql, dateDebut, dateDebut, dateDebut, dateFin);

            if (!Utilities.isNotEmpty(rows)){
                throw new FunctionalException(functionalError.DATA_EMPTY("", Locale.FRANCE));
            }

            Map<String, StatCaisseDto> resultatGroupeParDate = new HashMap<>();

            for (Map<String, Object> row : rows) {
                String date         = (String) row.get("date");
                Double totalEntrees = (Double) row.get("total_entrees");
                Double totalSortie  = (Double) row.get("total_sorties");

                // Si la date n'existe pas encore dans la structure de données, créez-la
                if (!resultatGroupeParDate.containsKey(date)) {
                    StatCaisseDto statCaisseDto = new StatCaisseDto();
                    statCaisseDto.setDate(date);
                    statCaisseDto.setDatas(new ArrayList<>());
                    resultatGroupeParDate.put(date, statCaisseDto);
                }

                StatCaisseDto statCaisseDto = resultatGroupeParDate.get(date);
                StatutDto     debit         = new StatutDto();
                debit.setKey("montantTotalEntre");
                debit.setValue(totalEntrees);
                statCaisseDto.getDatas().add(debit);
                StatutDto credit = new StatutDto();
                credit.setKey("montantTotalSortie");
                credit.setValue(totalSortie);
                statCaisseDto.getDatas().add(credit);
            }

            List<StatCaisseDto>       caisseDtos     = new ArrayList<>(resultatGroupeParDate.values());
            Comparator<StatCaisseDto> dateComparator = Comparator.comparing(StatCaisseDto::getDate);
            caisseDtos.sort(dateComparator);
            response.setItems(caisseDtos);

        } else if ("annee".equalsIgnoreCase(granularite)) {
            sql  = "SELECT DATE_FORMAT(date_range.date, '%Y') AS date,\n" +
                    "                    COALESCE(SUM(CASE WHEN c.type = 'ENTREE' THEN c.montant_entre ELSE 0 END), 0) AS total_entrees,\n" +
                    "                    COALESCE(SUM(CASE WHEN c.type = 'SORTIE' THEN c.montant_sortie ELSE 0 END), 0) AS total_sorties\n" +
                    "                FROM\n" +
                    "                    (\n" +
                    "                     SELECT DATE_ADD(?, INTERVAL n YEAR) AS date\n" +
                    "                     FROM (\n" +
                    "                       SELECT t0 + t1 * 10 AS n \n" +
                    "                       FROM (\n" +
                    "                         SELECT 0 AS t0 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9\n" +
                    "                       ) AS t0,\n" +
                    "                       (\n" +
                    "                         SELECT 0 AS t1 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9\n" +
                    "                       ) AS t1\n" +
                    "                     ) AS Numbers\n" +
                    "                     WHERE DATE_ADD(?, INTERVAL n YEAR) BETWEEN ? AND ?\n" +
                    "                    ) AS date_range\n" +
                    "                LEFT JOIN\n" +
                    "                    caisse c ON DATE_FORMAT(c.created_at, '%Y') = DATE_FORMAT(date_range.date, '%Y') AND c.is_deleted = false\n" +
                    "                GROUP BY\n" +
                    "                    date_range.date;";
            rows = mysqlTemplate.queryForList(sql, dateDebut, dateDebut, dateDebut, dateFin);

            if (!Utilities.isNotEmpty(rows)){
                throw new FunctionalException(functionalError.DATA_EMPTY("", Locale.FRANCE));
            }

            Map<String, StatCaisseDto> resultatGroupeParDate = new HashMap<>();

            for (Map<String, Object> row : rows) {
                String date         = (String) row.get("date");
                Double totalEntrees = (Double) row.get("total_entrees");
                Double totalSortie  = (Double) row.get("total_sorties");

                // Si la date n'existe pas encore dans la structure de données, créez-la
                if (!resultatGroupeParDate.containsKey(date)) {
                    StatCaisseDto statCaisseDto = new StatCaisseDto();
                    statCaisseDto.setDate(date);
                    statCaisseDto.setDatas(new ArrayList<>());
                    resultatGroupeParDate.put(date, statCaisseDto);
                }

                StatCaisseDto statCaisseDto = resultatGroupeParDate.get(date);
                StatutDto     debit         = new StatutDto();
                debit.setKey("montantTotalEntre");
                debit.setValue(totalEntrees);
                statCaisseDto.getDatas().add(debit);
                StatutDto credit = new StatutDto();
                credit.setKey("montantTotalSortie");
                credit.setValue(totalSortie);
                statCaisseDto.getDatas().add(credit);
            }

            List<StatCaisseDto>       caisseDtos     = new ArrayList<>(resultatGroupeParDate.values());
            Comparator<StatCaisseDto> dateComparator = Comparator.comparing(StatCaisseDto::getDate);
            caisseDtos.sort(dateComparator);
            response.setItems(caisseDtos);

        }

        Status status = new Status();
        status.setCode(StatusCode.SUCCESS);
        status.setMessage(StatusMessage.SUCCESS);
        response.setHttpCode(HttpStatus.OK.value());
        response.setStatus(status);
        response.setHasError(false);
        return response;
    }
}