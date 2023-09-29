package ci.saydos.markazcompta.business;

import ci.saydos.markazcompta.utils.Utilities;
import ci.saydos.markazcompta.utils.contract.Request;
import ci.saydos.markazcompta.utils.contract.Response;
import ci.saydos.markazcompta.utils.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Log
@Component
@RequiredArgsConstructor
public class StatistiqueBusiness {
    private final JdbcTemplate mysqlTemplate;

    public Response<Map<String, Object>> demande(Request<DemandeHistoriqueDto> request) throws ParseException {
        Response<Map<String, Object>> response = new Response<>();

        DemandeHistoriqueDto dto       = request.getData();
        String               dateParam = dto.getCreatedAt();
        String               dateDebut = dto.getCreatedAtParam().getStart();
        String               dateFin   = dto.getCreatedAtParam().getEnd();
        System.out.println("dateParam: " + dateParam);
        List<Map<String, Object>> rows     = new ArrayList<>();
        Map<String, Object>       countMap = new HashMap<>();

        if (Utilities.notBlank(dateParam)) {
            String sql = "SELECT dh.statut, COUNT(*) AS nombre_total_demandes_par_type " +
                    "FROM demande d " +
                    "JOIN demande_historique dh ON d.id = dh.id_demande " +
                    "WHERE dh.created_at = ? " +
                    "GROUP BY dh.statut";

            Timestamp timestampParam = Utilities.convertStringToTimestamp(dateParam);
            rows = mysqlTemplate.queryForList(
                    sql,
                    timestampParam
            );

            Long totalDemandes = getTotalDemandesByDate(dateParam);
            countMap.put("TotalDemandes", totalDemandes);
        }
        if (Utilities.notBlank(dateDebut) && Utilities.notBlank(dateFin)) {
            String sql = "SELECT dh.statut, COUNT(*) AS nombre_total_demandes_par_type " +
                    "FROM demande d " +
                    "JOIN demande_historique dh ON d.id = dh.id_demande " +
                    "WHERE dh.created_at BETWEEN ? AND ? " + // Utilisez BETWEEN pour une plage de dates
                    "GROUP BY dh.statut";

            Timestamp timestampDebut = Utilities.convertStringToTimestamp(dateDebut);
            Timestamp timestampFin   = Utilities.convertStringToTimestamp(dateFin);
            rows = mysqlTemplate.queryForList(
                    sql,
                    timestampDebut,
                    timestampFin
            );

            Long totalDemandes = getTotalDemandesByDateRange(dateDebut, dateFin);
            countMap.put("TotalDemandes", totalDemandes);
        }


        for (Map<String, Object> row : rows) {
            String statut = (String) row.get("statut"); // Utilisez "statut" au lieu de "type_demande"
            Long   count  = (Long) row.get("nombre_total_demandes_par_type");
            countMap.put(statut, count); // Utilisez "statut" comme clé
        }

        response.setItem(countMap);
        return response;
    }

    public Response<Map<String, Object>> caisse(Request<DemandeHistoriqueDto> request) throws ParseException {
        Response<Map<String, Object>> response = new Response<>();

        DemandeHistoriqueDto dto       = request.getData();
        String               dateParam = dto.getCreatedAt();
        String               dateDebut = dto.getCreatedAtParam().getStart();
        String               dateFin   = dto.getCreatedAtParam().getEnd();
        System.out.println("dateParam: " + dateParam);
        List<Map<String, Object>> rows     = new ArrayList<>();
        Map<String, Object>       countMap = new HashMap<>();

        if (Utilities.notBlank(dateParam)) {
            Double montantTotal       = getMontantDisponibleByDate(dateParam);
            Double montantTotalSortie = getTotalMontantsSortieByDate(dateParam);
            Double montantTotalEntre  = getTotalMontantsEntresByDate(dateParam);
            countMap.put("montantTotal", montantTotal);
            countMap.put("montantTotalSortie", montantTotalSortie);
            countMap.put("montantTotalEntre", montantTotalEntre);
        }
        if (Utilities.notBlank(dateDebut) && Utilities.notBlank(dateFin)) {
            Double montantTotal       = getMontantDisponibleByDateRange(dateDebut, dateFin);
            Double montantTotalSortie = getTotalMontantsSortieByDateRange(dateDebut, dateFin);
            Double montantTotalEntre  = getTotalMontantsEntresByDateRange(dateDebut, dateFin);
            countMap.put("montantTotal", montantTotal);
            countMap.put("montantTotalSortie", montantTotalSortie);
            countMap.put("montantTotalEntre", montantTotalEntre);
        }

        response.setItem(countMap);
        return response;
    }

    public Long getTotalDemandesByDate(String dateParam) throws ParseException {
        String sql = "SELECT COUNT(*) AS nombre_total_demandes " +
                "FROM demande d " +
                "JOIN demande_historique dh ON d.id = dh.id_demande " +
                "WHERE dh.created_at = ?";

        Timestamp timestampParam = Utilities.convertStringToTimestamp(dateParam);

        return mysqlTemplate.queryForObject(sql, Long.class, timestampParam);
    }

    public Long getTotalDemandesByDateRange(String dateDebut, String dateFin) throws ParseException {
        String sql = "SELECT COUNT(*) AS nombre_total_demandes " +
                "FROM demande d " +
                "JOIN demande_historique dh ON d.id = dh.id_demande " +
                "WHERE dh.created_at BETWEEN ? AND ?";

        Timestamp timestampDebut = Utilities.convertStringToTimestamp(dateDebut);
        Timestamp timestampFin   = Utilities.convertStringToTimestamp(dateFin);

        return mysqlTemplate.queryForObject(sql, Long.class, timestampDebut, timestampFin);
    }

    public Double getMontantDisponibleByDate(String dateParam) throws ParseException {
        String sql = "SELECT SUM(CASE WHEN created_at <= ? THEN montant_entre - montant_sortie ELSE 0 END) AS montant_disponible FROM caisse";

        Timestamp timestampParam = Utilities.convertStringToTimestamp(dateParam);
        return mysqlTemplate.queryForObject(sql, Double.class, timestampParam);
    }

    public Double getMontantDisponibleByDateRange(String dateDebut, String dateFin) throws ParseException {
        String sql = "SELECT SUM(CASE WHEN created_at BETWEEN ? AND ? THEN montant_entre - montant_sortie ELSE 0 END) AS montant_disponible FROM caisse";

        Timestamp timestampDebut = Utilities.convertStringToTimestamp(dateDebut);
        Timestamp timestampFin   = Utilities.convertStringToTimestamp(dateFin);
        return mysqlTemplate.queryForObject(sql, Double.class, timestampDebut, timestampFin);
    }

    public Double getTotalMontantsEntresByDate(String dateParam) throws ParseException {
        String    sql            = "SELECT SUM(montant_entre) AS total_montants_entres FROM caisse WHERE created_at = ?";
        Timestamp timestampParam = Utilities.convertStringToTimestamp(dateParam);
        return mysqlTemplate.queryForObject(sql, Double.class, timestampParam);
    }

    public Double getTotalMontantsEntresByDateRange(String dateDebut, String dateFin) throws ParseException {
        String    sql            = "SELECT SUM(montant_entre) AS total_montants_entres FROM caisse WHERE created_at BETWEEN ? AND ?";
        Timestamp timestampDebut = Utilities.convertStringToTimestamp(dateDebut);
        Timestamp timestampFin   = Utilities.convertStringToTimestamp(dateFin);
        return mysqlTemplate.queryForObject(sql, Double.class, timestampDebut, timestampFin);
    }

    public Double getTotalMontantsSortieByDate(String dateParam) throws ParseException {
        String    sql            = "SELECT SUM(montant_sortie) AS total_montants_entres FROM caisse WHERE created_at = ?";
        Timestamp timestampParam = Utilities.convertStringToTimestamp(dateParam);
        return mysqlTemplate.queryForObject(sql, Double.class, timestampParam);
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
            sql = "\n" +
                    "WITH RECURSIVE Days AS (\n" +
                    "  SELECT\n" +
                    "    DATE_FORMAT(?, '%Y-%m-%d') AS day_start,\n" +
                    "    DATE_FORMAT(?, '%Y-%m-%d') AS day\n" +
                    "  UNION ALL\n" +
                    "  SELECT\n" +
                    "    DATE_ADD(day_start, INTERVAL 1 DAY) AS day_start,\n" +
                    "    DATE_FORMAT(DATE_ADD(day_start, INTERVAL 1 DAY), '%Y-%m-%d') AS day\n" +
                    "  FROM\n" +
                    "    Days\n" +
                    "  WHERE\n" +
                    "    day_start < ?\n" +
                    ")\n" +
                    "\n" +
                    "SELECT\n" +
                    "  CONCAT(YEAR(Days.day), '-', LPAD(MONTH(Days.day), 2, '0'), '-', LPAD(DAY(Days.day), 2, '0')) AS date,\n" +
                    "  S.statut,\n" +
                    "  COALESCE(COUNT(DH.id), 0) AS nombre_demandes\n" +
                    "FROM\n" +
                    "  Days\n" +
                    "CROSS JOIN\n" +
                    "  (SELECT DISTINCT statut FROM demande_historique) S\n" +
                    "LEFT JOIN\n" +
                    "  demande_historique DH ON YEAR(DH.created_at) = YEAR(Days.day) AND MONTH(DH.created_at) = MONTH(Days.day) AND DAY(DH.created_at) = DAY(Days.day) AND DH.statut = S.statut\n" +
                    "GROUP BY\n" +
                    "  date, S.statut\n" +
                    "ORDER BY\n" +
                    "  date, S.statut;";

            rows = mysqlTemplate.queryForList(sql, dateDebut, dateDebut, dateFin);

            List<StatDemandeDto> statDemandeDtos = new ArrayList<>();

            Map<String, StatDemandeDto> resultatGroupeParDate = new HashMap<>();

            for (Map<String, Object> row : rows) {
                String date           = (String) row.get("date");
                String statut         = (String) row.get("statut");
                Long   nombreDemandes = (Long) row.get("nombre_demandes");

                // Si la date n'existe pas encore dans la structure de données, créez-la
                if (!resultatGroupeParDate.containsKey(date)) {
                    StatDemandeDto statDemandeDto = new StatDemandeDto();
                    statDemandeDto.setDate(date);
                    statDemandeDto.setDatas(new ArrayList<>());
                    resultatGroupeParDate.put(date, statDemandeDto);
                }

                StatDemandeDto statDemandeDto = resultatGroupeParDate.get(date);
                StatutDto      statutDto      = new StatutDto();
                statutDto.setKey(statut);
                statutDto.setValue(nombreDemandes);

                statDemandeDto.getDatas().add(statutDto);
            }

            statDemandeDtos.addAll(resultatGroupeParDate.values());

            Comparator<StatDemandeDto> dateComparator = Comparator.comparing(StatDemandeDto::getDate);
            Collections.sort(statDemandeDtos, dateComparator);
            response.setItems(statDemandeDtos);
            return response;

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


            List<StatDemandeDto>        statDemandeDtos       = new ArrayList<>();
            Map<String, StatDemandeDto> resultatGroupeParDate = new HashMap<>();

            for (Map<String, Object> row : rows) {
                String date           = (String) row.get("date");
                String statut         = (String) row.get("statut");
                Long   nombreDemandes = (Long) row.get("nombre_demandes");

                if (!resultatGroupeParDate.containsKey(date)) {
                    StatDemandeDto statDemandeDto = new StatDemandeDto();
                    statDemandeDto.setDate(date);
                    statDemandeDto.setDatas(new ArrayList<>());
                    resultatGroupeParDate.put(date, statDemandeDto);
                }

                StatDemandeDto statDemandeDto = resultatGroupeParDate.get(date);

                StatutDto statutDto = new StatutDto();
                statutDto.setKey(statut);
                statutDto.setValue((long) nombreDemandes);

                statDemandeDto.getDatas().add(statutDto);
            }


            statDemandeDtos.addAll(resultatGroupeParDate.values());
            Comparator<StatDemandeDto> dateComparator = Comparator.comparing(StatDemandeDto::getDate);
            Collections.sort(statDemandeDtos, dateComparator);
            response.setItems(statDemandeDtos);
            return response;

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


            // Créez une liste d'objets StatDemandeDto
            List<StatDemandeDto> statDemandeDtos = new ArrayList<>();

// Utilisez une structure de données pour regrouper les résultats par date
            Map<String, StatDemandeDto> resultatGroupeParDate = new HashMap<>();

            for (Map<String, Object> row : rows) {
                String date                = (String) row.get("annee");
                String statut              = (String) row.get("statut");
                Long   nombreDemandes      = (Long) row.get("nombre_demandes");
                long   nombreDemandesValue = (nombreDemandes != null) ? nombreDemandes.longValue() : 0L;


                // Si la date n'existe pas encore dans la structure de données, créez-la
                if (!resultatGroupeParDate.containsKey(date)) {
                    StatDemandeDto statDemandeDto = new StatDemandeDto();
                    statDemandeDto.setDate(date);
                    statDemandeDto.setDatas(new ArrayList<>());
                    resultatGroupeParDate.put(date, statDemandeDto);
                }

                // Récupérez l'objet StatDemandeDto correspondant à la date
                StatDemandeDto statDemandeDto = resultatGroupeParDate.get(date);

                // Créez un objet StatutDto
                StatutDto statutDto = new StatutDto();
                statutDto.setKey(statut);
                statutDto.setValue((long) nombreDemandes);

                // Ajoutez le StatutDto à la liste "datas" de StatDemandeDto
                statDemandeDto.getDatas().add(statutDto);
            }

// Ajoutez les objets StatDemandeDto à la liste
            statDemandeDtos.addAll(resultatGroupeParDate.values());

// Créez une instance de Response<StatDemandeDto> pour la réponse finale
            Comparator<StatDemandeDto> dateComparator = Comparator.comparing(StatDemandeDto::getDate);

// Triez la liste d'objets StatDemandeDto par date
            Collections.sort(statDemandeDtos, dateComparator);

            response.setItems(statDemandeDtos);

            return response;
        }

        return response;
    }

    public Response<StatCaisseDto> getStatistiquesCaisseParPeriode(Request<CaisseDto> request) {
        CaisseDto                dto         = request.getData();
        String                   granularite = dto.getGranularite();
        String                   dateDebut   = dto.getCreatedAtParam().getStart();
        String                   dateFin     = dto.getCreatedAtParam().getEnd();
        Response<StatDemandeDto> response    = new Response<>();

        String                    sql  = "";
        List<Map<String, Object>> rows = new ArrayList<>();

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
            List<StatCaisseDto> caisseDtos = new ArrayList<>();

            Map<String, StatCaisseDto> resultatGroupeParDate = new HashMap<>();

            for (Map<String, Object> row : rows) {
                String date         = (String) row.get("date");
                Long   totalEntrees = (Long) row.get("total_entrees");
                Long   totalSortie  = (Long) row.get("total_entrees");

                // Si la date n'existe pas encore dans la structure de données, créez-la
                if (!resultatGroupeParDate.containsKey(date)) {
                    StatCaisseDto statCaisseDto = new StatCaisseDto();
                    statCaisseDto.setDate(date);
                    statCaisseDto.setDatas(new ArrayList<>());
                    resultatGroupeParDate.put(date, statCaisseDto);
                }

                StatCaisseDto statCaisseDto = resultatGroupeParDate.get(date);
                StatutDto      statutDto      = new StatutDto();
                statutDto.setKey(statut);
                statutDto.setValue(nombreDemandes);

                statDemandeDto.getDatas().add(statutDto);
            }

            statDemandeDtos.addAll(resultatGroupeParDate.values());

            Comparator<StatDemandeDto> dateComparator = Comparator.comparing(StatDemandeDto::getDate);
            Collections.sort(statDemandeDtos, dateComparator);
            response.setItems(statDemandeDtos);
            return response;

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
        }
        return null;
    }
}