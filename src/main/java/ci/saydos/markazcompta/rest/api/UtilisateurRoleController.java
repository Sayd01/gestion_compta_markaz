

/*
 * Java controller for entity table utilisateur_role 
 * Created on 2023-08-06 ( Time 01:31:06 )
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
Controller for table "utilisateur_role"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/utilisateurRole")
public class UtilisateurRoleController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<UtilisateurRoleDto> controllerFactory;
	@Autowired
	private UtilisateurRoleBusiness utilisateurRoleBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public ResponseEntity<Response<UtilisateurRoleDto>> create(@RequestBody Request<UtilisateurRoleDto> request) throws Exception {
    	log.info("start method /utilisateurRole/create");
        Response<UtilisateurRoleDto> response = controllerFactory.create(utilisateurRoleBusiness, request, FunctionalityEnum.CREATE_UTILISATEUR_ROLE);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.buildAndExpand(response.getItems().get(0))
				.toUri();
		log.info("end method /utilisateurRole/create");
        return ResponseEntity.created(uri).body(response);
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<UtilisateurRoleDto> update(@RequestBody Request<UtilisateurRoleDto> request) throws Exception{
    	log.info("start method /utilisateurRole/update");
        Response<UtilisateurRoleDto> response = controllerFactory.update(utilisateurRoleBusiness, request, FunctionalityEnum.UPDATE_UTILISATEUR_ROLE);
		log.info("end method /utilisateurRole/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<UtilisateurRoleDto> delete(@RequestBody Request<UtilisateurRoleDto> request) throws Exception{
    	log.info("start method /utilisateurRole/delete");
        Response<UtilisateurRoleDto> response = controllerFactory.delete(utilisateurRoleBusiness, request, FunctionalityEnum.DELETE_UTILISATEUR_ROLE);
		log.info("end method /utilisateurRole/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<UtilisateurRoleDto> getByCriteria(@RequestBody Request<UtilisateurRoleDto> request) throws Exception{
    	log.info("start method /utilisateurRole/getByCriteria");
        Response<UtilisateurRoleDto> response = controllerFactory.getByCriteria(utilisateurRoleBusiness, request, FunctionalityEnum.VIEW_UTILISATEUR_ROLE);
		log.info("end method /utilisateurRole/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<UtilisateurRoleDto> custom(@RequestBody Request<UtilisateurRoleDto> request) throws Exception{
	  log.info("UtilisateurRoleDto method /$/custom");
	  Response<UtilisateurRoleDto> response = new Response<UtilisateurRoleDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = utilisateurRoleBusiness.custom(request, locale);
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
	  slf4jLogger.info("end method /UtilisateurRoleDto/custom");
	  return response;
	 }
}
