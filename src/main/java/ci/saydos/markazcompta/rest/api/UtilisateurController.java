

/*
 * Java controller for entity table utilisateur 
 * Created on 2023-08-08 ( Time 19:02:56 )
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
Controller for table "utilisateur"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/utilisateur")
public class UtilisateurController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<UtilisateurDto> controllerFactory;
	@Autowired
	private UtilisateurBusiness utilisateurBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public ResponseEntity<Response<UtilisateurDto>> create(@RequestBody Request<UtilisateurDto> request) throws Exception {
    	log.info("start method /utilisateur/create");
        Response<UtilisateurDto> response = controllerFactory.create(utilisateurBusiness, request, FunctionalityEnum.CREATE_UTILISATEUR);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.buildAndExpand(response.getItems().get(0))
				.toUri();
		log.info("end method /utilisateur/create");
        return ResponseEntity.created(uri).body(response);
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<UtilisateurDto> update(@RequestBody Request<UtilisateurDto> request) throws Exception{
    	log.info("start method /utilisateur/update");
        Response<UtilisateurDto> response = controllerFactory.update(utilisateurBusiness, request, FunctionalityEnum.UPDATE_UTILISATEUR);
		log.info("end method /utilisateur/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<UtilisateurDto> delete(@RequestBody Request<UtilisateurDto> request) throws Exception{
    	log.info("start method /utilisateur/delete");
        Response<UtilisateurDto> response = controllerFactory.delete(utilisateurBusiness, request, FunctionalityEnum.DELETE_UTILISATEUR);
		log.info("end method /utilisateur/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<UtilisateurDto> getByCriteria(@RequestBody Request<UtilisateurDto> request) throws Exception{
    	log.info("start method /utilisateur/getByCriteria");
        Response<UtilisateurDto> response = controllerFactory.getByCriteria(utilisateurBusiness, request, FunctionalityEnum.VIEW_UTILISATEUR);
		log.info("end method /utilisateur/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<UtilisateurDto> custom(@RequestBody Request<UtilisateurDto> request) throws Exception{
	  log.info("UtilisateurDto method /$/custom");
	  Response<UtilisateurDto> response = new Response<UtilisateurDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = utilisateurBusiness.custom(request, locale);
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
	  slf4jLogger.info("end method /UtilisateurDto/custom");
	  return response;
	 }
}
