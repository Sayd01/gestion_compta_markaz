

/*
 * Java controller for entity table utilisateur_direction 
 * Created on 2023-08-29 ( Time 13:35:25 )
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
Controller for table "utilisateur_direction"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/utilisateurDirection")
public class UtilisateurDirectionController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<UtilisateurDirectionDto> controllerFactory;
	@Autowired
	private UtilisateurDirectionBusiness utilisateurDirectionBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public ResponseEntity<Response<UtilisateurDirectionDto>> create(@RequestBody Request<UtilisateurDirectionDto> request) throws Exception {
    	log.info("start method /utilisateurDirection/create");
        Response<UtilisateurDirectionDto> response = controllerFactory.create(utilisateurDirectionBusiness, request, FunctionalityEnum.CREATE_UTILISATEUR_DIRECTION);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.buildAndExpand(response.getItems().get(0))
				.toUri();
		log.info("end method /utilisateurDirection/create");
        return ResponseEntity.created(uri).body(response);
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<UtilisateurDirectionDto> update(@RequestBody Request<UtilisateurDirectionDto> request) throws Exception{
    	log.info("start method /utilisateurDirection/update");
        Response<UtilisateurDirectionDto> response = controllerFactory.update(utilisateurDirectionBusiness, request, FunctionalityEnum.UPDATE_UTILISATEUR_DIRECTION);
		log.info("end method /utilisateurDirection/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<UtilisateurDirectionDto> delete(@RequestBody Request<UtilisateurDirectionDto> request) throws Exception{
    	log.info("start method /utilisateurDirection/delete");
        Response<UtilisateurDirectionDto> response = controllerFactory.delete(utilisateurDirectionBusiness, request, FunctionalityEnum.DELETE_UTILISATEUR_DIRECTION);
		log.info("end method /utilisateurDirection/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<UtilisateurDirectionDto> getByCriteria(@RequestBody Request<UtilisateurDirectionDto> request) throws Exception{
    	log.info("start method /utilisateurDirection/getByCriteria");
        Response<UtilisateurDirectionDto> response = controllerFactory.getByCriteria(utilisateurDirectionBusiness, request, FunctionalityEnum.VIEW_UTILISATEUR_DIRECTION);
		log.info("end method /utilisateurDirection/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<UtilisateurDirectionDto> custom(@RequestBody Request<UtilisateurDirectionDto> request) throws Exception{
	  log.info("UtilisateurDirectionDto method /$/custom");
	  Response<UtilisateurDirectionDto> response = new Response<UtilisateurDirectionDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = utilisateurDirectionBusiness.custom(request, locale);
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
	  slf4jLogger.info("end method /UtilisateurDirectionDto/custom");
	  return response;
	 }
}
