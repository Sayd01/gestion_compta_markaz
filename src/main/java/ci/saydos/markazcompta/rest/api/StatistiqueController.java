package ci.saydos.markazcompta.rest.api;

import ci.saydos.markazcompta.business.StatistiqueBusiness;
import ci.saydos.markazcompta.utils.contract.Request;
import ci.saydos.markazcompta.utils.contract.Response;
import ci.saydos.markazcompta.utils.dto.DemandeHistoriqueDto;
import ci.saydos.markazcompta.utils.dto.StatDemandeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/statistique")
public class StatistiqueController {
    private final StatistiqueBusiness statistiqueBusiness;
    @RequestMapping(value="/demande",method= RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<Map<String, Object>> demande (@RequestBody Request<DemandeHistoriqueDto> request) throws ParseException {
        return statistiqueBusiness.demande(request);
    }

    @RequestMapping(value="/caisse",method= RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<Map<String, Object>> caisse (@RequestBody Request<DemandeHistoriqueDto> request) throws ParseException {
        return statistiqueBusiness.caisse(request);
    }

    @PostMapping("/demandes-par-periode")
    public Response<StatDemandeDto> getStatistiquesDemandesParMois(@RequestBody Request<DemandeHistoriqueDto> request) {
        Response<StatDemandeDto> statistiques = statistiqueBusiness.getStatistiquesParMois(request);
        System.out.println(statistiques);
        return statistiques;
    }


}
