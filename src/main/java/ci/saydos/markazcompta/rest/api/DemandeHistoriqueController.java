

/*
 * Java controller for entity table demande_historique 
 * Created on 2023-08-06 ( Time 01:31:05 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

package ci.saydos.markazcompta.rest.api;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;

import java.net.URI;
import java.util.Locale;

import ci.saydos.markazcompta.utils.*;
import ci.saydos.markazcompta.utils.dto.*;
import ci.saydos.markazcompta.utils.contract.*;
import ci.saydos.markazcompta.utils.contract.Request;
import ci.saydos.markazcompta.utils.contract.Response;
import ci.saydos.markazcompta.utils.enums.FunctionalityEnum;
import ci.saydos.markazcompta.business.*;
import ci.saydos.markazcompta.rest.fact.ControllerFactory;

/**
Controller for table "demande_historique"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/demandeHistorique")
public class DemandeHistoriqueController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<DemandeHistoriqueDto> controllerFactory;
	@Autowired
	private DemandeHistoriqueBusiness demandeHistoriqueBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public ResponseEntity<Response<DemandeHistoriqueDto>> create(@RequestBody Request<DemandeHistoriqueDto> request) throws Exception {
    	log.info("start method /demandeHistorique/create");
        Response<DemandeHistoriqueDto> response = controllerFactory.create(demandeHistoriqueBusiness, request, FunctionalityEnum.CREATE_DEMANDE_HISTORIQUE);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.buildAndExpand(response.getItems().get(0))
				.toUri();
		log.info("end method /demandeHistorique/create");
        return ResponseEntity.created(uri).body(response);
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<DemandeHistoriqueDto> update(@RequestBody Request<DemandeHistoriqueDto> request) throws Exception{
    	log.info("start method /demandeHistorique/update");
        Response<DemandeHistoriqueDto> response = controllerFactory.update(demandeHistoriqueBusiness, request, FunctionalityEnum.UPDATE_DEMANDE_HISTORIQUE);
		log.info("end method /demandeHistorique/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<DemandeHistoriqueDto> delete(@RequestBody Request<DemandeHistoriqueDto> request) throws Exception{
    	log.info("start method /demandeHistorique/delete");
        Response<DemandeHistoriqueDto> response = controllerFactory.delete(demandeHistoriqueBusiness, request, FunctionalityEnum.DELETE_DEMANDE_HISTORIQUE);
		log.info("end method /demandeHistorique/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<DemandeHistoriqueDto> getByCriteria(@RequestBody Request<DemandeHistoriqueDto> request) throws Exception{
    	log.info("start method /demandeHistorique/getByCriteria");
        Response<DemandeHistoriqueDto> response = controllerFactory.getByCriteria(demandeHistoriqueBusiness, request, FunctionalityEnum.VIEW_DEMANDE_HISTORIQUE);
		log.info("end method /demandeHistorique/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<DemandeHistoriqueDto> custom(@RequestBody Request<DemandeHistoriqueDto> request) throws Exception{
	  log.info("DemandeHistoriqueDto method /$/custom");
	  Response<DemandeHistoriqueDto> response = new Response<DemandeHistoriqueDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = demandeHistoriqueBusiness.custom(request, locale);
	   } else {
	    slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
	      response.getStatus().getMessage());
	    return response;
	   }
	   if (!response.isHasError()) {
	    slf4jLogger.info("end method custom");
	    slf4jLogger.info("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS);
	   } else {
	    slf4jLogger.info("Erreur| code: {} -  message: {}", response.getStatus().getCode(),
	      response.getStatus().getMessage());
	   }
	  } catch (CannotCreateTransactionException e) {
	   exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
	  } catch (TransactionSystemException e) {
	   exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
	  }
	  slf4jLogger.info("end method /DemandeHistoriqueDto/custom");
	  return response;
	 }
}
