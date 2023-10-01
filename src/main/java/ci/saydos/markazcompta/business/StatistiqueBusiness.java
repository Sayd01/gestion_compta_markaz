package ci.saydos.markazcompta.business;

import ci.saydos.markazcompta.utils.FunctionalError;
import ci.saydos.markazcompta.utils.Utilities;
import ci.saydos.markazcompta.utils.contract.Request;
import ci.saydos.markazcompta.utils.contract.Response;
import ci.saydos.markazcompta.utils.dto.*;
import ci.saydos.markazcompta.utils.enums.StatutDemandeEnum;
import ci.saydos.markazcompta.utils.exception.InternalErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

@Log
@Component
@RequiredArgsConstructor
public class StatistiqueBusiness {
    private final JdbcTemplate    mysqlTemplate;
    private final FunctionalError functionalError;
    private final String          formatDate     = "yyyy-MM-dd";
    private final String          formatDateTime = "yyyy-MM-dd HH:mm:ss";


    public Response<Map<String, Object>> demande(Request<DemandeHistoriqueDto> request) throws ParseException {
        Response<Map<String, Object>> response  = new Response<>();
        DemandeHistoriqueDto          dto       = request.getData();
        String                        dateDebut = Utilities.formatDate(dto.getCreatedAtParam().getStart(), formatDateTime);
        String                        dateFin   = Utilities.formatDate(dto.getCreatedAtParam().getEnd(), formatDateTime);
        List<Map<String, Object>>     rows      = new ArrayList<>();
        Map<String, Object>           countMap  = new HashMap<>();

        for (StatutDemandeEnum statut : StatutDemandeEnum.values()) {
            countMap.put(statut.toString().toLowerCase(), 0L);
        }

        if (Utilities.notBlank(dateDebut) && Utilities.notBlank(dateFin)) {
            String sql = "SELECT d.statut, COUNT(*) AS nombre_total_demandes_par_type " +
                    "FROM demande d " +
                    "WHERE d.created_at BETWEEN ? AND ? " + // Utilisez BETWEEN pour une plage de dates
                    "GROUP BY d.statut";

            Timestamp timestampDebut = Utilities.convertStringToTimestamp(dateDebut);
            Timestamp timestampFin   = Utilities.convertStringToTimestamp(dateFin);
            rows = mysqlTemplate.queryForList(
                    sql,
                    timestampDebut,
                    timestampFin
            );

            Long totalDemandes = getTotalDemandesByDateRange(dateDebut, dateFin);
            countMap.put("totalDemandes", totalDemandes);
        }


        if (Utilities.isNotEmpty(rows)) {
            for (Map<String, Object> row : rows) {
                String statut = (String) row.get("statut");
                Long   count  = (Long) row.get("nombre_total_demandes_par_type");
                countMap.put(statut.toLowerCase(), count);
            }

        } else {
            throw new InternalErrorException(functionalError.DATA_EMPTY("", Locale.FRANCE));
        }


        response.setItem(countMap);
        return response;
    }

    public Response<Map<String, Object>> caisse(Request<DemandeHistoriqueDto> request) throws ParseException {
        Response<Map<String, Object>> response = new Response<>();
        DemandeHistoriqueDto dto       = request.getData();
        String                        dateDebut = Utilities.formatDate(dto.getCreatedAtParam().getStart(), formatDateTime);
        String                        dateFin   = Utilities.formatDate(dto.getCreatedAtParam().getEnd(), formatDateTime);

        List<Map<String, Object>> rows     = new ArrayList<>();
        Map<String, Object>       countMap = new HashMap<>();

        if (Utilities.notBlank(dateDebut) && Utilities.notBlank(dateFin)) {
            Double montantTotal       = getMontantDisponibleByDateRange(dateDebut, dateFin) != null ? getMontantDisponibleByDateRange(dateDebut, dateFin) : 0.0;
            Double montantTotalSortie = getTotalMontantsSortieByDateRange(dateDebut, dateFin) != null ? getTotalMontantsSortieByDateRange(dateDebut, dateFin) : 0.0;
            Double montantTotalEntre  = getTotalMontantsEntresByDateRange(dateDebut, dateFin) != null ? getTotalMontantsEntresByDateRange(dateDebut, dateFin) : 0.0;

            System.out.println(montantTotalEntre);
            countMap.put("montantTotal", montantTotal);
            countMap.put("montantTotalSortie", montantTotalSortie);
            countMap.put("montantTotalEntre", montantTotalEntre);
        }

        response.setItem(countMap);
        return response;
    }

    public Long getTotalDemandesByDateRange(String dateDebut, String dateFin) throws ParseException {
        String sql = "SELECT COUNT(*) AS nombre_total_demandes " +
                "FROM demande d " +
                "WHERE d.created_at BETWEEN ? AND ?";

        Timestamp timestampDebut = Utilities.convertStringToTimestamp(dateDebut);
        Timestamp timestampFin   = Utilities.convertStringToTimestamp(dateFin);

        return mysqlTemplate.queryForObject(sql, Long.class, timestampDebut, timestampFin);
    }

    public Double getMontantDisponibleByDateRange(String dateDebut, String dateFin) throws ParseException {
        String sql = "SELECT SUM(CASE WHEN created_at BETWEEN ? AND ? THEN montant_entre - montant_sortie ELSE 0 END) AS montant_disponible FROM caisse";

        Timestamp timestampDebut = Utilities.convertStringToTimestamp(dateDebut);
        Timestamp timestampFin   = Utilities.convertStringToTimestamp(dateFin);
        return mysqlTemplate.queryForObject(sql, Double.class, timestampDebut, timestampFin);
    }

    public Double getTotalMontantsEntresByDateRange(String dateDebut, String dateFin) throws ParseException {
        String    sql            = "SELECT SUM(montant_entre) AS total_montants_entres FROM caisse WHERE created_at BETWEEN ? AND ?";
        Timestamp timestampDebut = Utilities.convertStringToTimestamp(dateDebut);
        Timestamp timestampFin   = Utilities.convertStringToTimestamp(dateFin);
        return mysqlTemplate.queryForObject(sql, Double.class, timestampDebut, timestampFin);
    }

    public Double getTotalMontantsSortieByDateRange(String dateDebut, String dateFin) throws ParseException {
        String    sql            = "SELECT SUM(montant_sortie) AS total_montants_entres FROM caisse WHERE created_at BETWEEN ? AND ?";
        Timestamp timestampDebut = Utilities.convertStringToTimestamp(dateDebut);
        Timestamp timestampFin   = Utilities.convertStringToTimestamp(dateFin);
        return mysqlTemplate.queryForObject(sql, Double.class, timestampDebut, timestampFin);
    }

    public Response<StatDemandeDto> getStatistiquesParMois(Request<DemandeHistoriqueDto> request) {
        DemandeHistoriqueDto     dto         = request.getData();
        String                   dateDebut   = dto.getCreatedAtParam().getStart();
        String                   dateFin     = dto.getCreatedAtParam().getEnd();
        String                   granularite = dto.getGranularite();
        Response<StatDemandeDto> response    = new Response<>();



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
                    "  demande DH ON YEAR(DH.created_at) = YEAR(Days.day) AND MONTH(DH.created_at) = MONTH(Days.day) AND DAY(DH.created_at) = DAY(Days.day) AND DH.statut = S.statut\n" +
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
                    Long    valeur    = statistiquesParDate.get(date).getOrDefault(statut, 0L);
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
                    "    (SELECT DISTINCT statut FROM demande_historique) AS statuts\n" +
                    "LEFT JOIN\n" +
                    "    demande_historique AS dh\n" +
                    "ON\n" +
                    "    DATE(dh.created_at) >= Months.start_month\n" +
                    "    AND DATE(dh.created_at) < Months.end_month\n" +
                    "    AND statuts.statut = dh.statut\n" +
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
                    Long    valeur    = statistiquesParDate.get(date).getOrDefault(statut, 0L);
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
            sql = "\n" +
                    "SELECT\n" +
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
                    "        SELECT DISTINCT statut FROM demande_historique\n" +
                    "    ) s\n" +
                    "LEFT JOIN\n" +
                    "    demande_historique dh\n" +
                    "ON\n" +
                    "    d.date = DATE(dh.created_at) AND s.statut = dh.statut\n" +
                    "GROUP BY\n" +
                    "    annee, s.statut\n" +
                    "ORDER BY\n" +
                    "    annee, s.statut;";

            rows = mysqlTemplate.queryForList(sql, dateDebut, dateFin);
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
                    Long    valeur    = statistiquesParDate.get(date).getOrDefault(statut, 0L);
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

        String                    sql  = "";
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
                    "    caisse c ON DATE(c.created_at) = date_range.date\n" +
                    "GROUP BY date_range.date;";

            rows = mysqlTemplate.queryForList(sql, dateDebut, dateDebut, dateDebut, dateFin);

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
                    "                    caisse c ON DATE_FORMAT(c.created_at, '%Y-%m') = DATE_FORMAT(date_range.date, '%Y-%m')\n" +
                    "                GROUP BY\n" +
                    "                    date_range.date;";

            rows = mysqlTemplate.queryForList(sql, dateDebut, dateDebut, dateDebut, dateFin);

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
                    "                    caisse c ON DATE_FORMAT(c.created_at, '%Y') = DATE_FORMAT(date_range.date, '%Y')\n" +
                    "                GROUP BY\n" +
                    "                    date_range.date;";
            rows = mysqlTemplate.queryForList(sql, dateDebut, dateDebut, dateDebut, dateFin);

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
        return response;
    }
}