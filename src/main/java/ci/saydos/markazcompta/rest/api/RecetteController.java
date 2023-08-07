

/*
 * Java controller for entity table recette 
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
Controller for table "recette"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/recette")
public class RecetteController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<RecetteDto> controllerFactory;
	@Autowired
	private RecetteBusiness recetteBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public ResponseEntity<Response<RecetteDto>> create(@RequestBody Request<RecetteDto> request) throws Exception {
    	log.info("start method /recette/create");
        Response<RecetteDto> response = controllerFactory.create(recetteBusiness, request, FunctionalityEnum.CREATE_RECETTE);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.buildAndExpand(response.getItems().get(0))
				.toUri();
		log.info("end method /recette/create");
        return ResponseEntity.created(uri).body(response);
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<RecetteDto> update(@RequestBody Request<RecetteDto> request) throws Exception{
    	log.info("start method /recette/update");
        Response<RecetteDto> response = controllerFactory.update(recetteBusiness, request, FunctionalityEnum.UPDATE_RECETTE);
		log.info("end method /recette/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<RecetteDto> delete(@RequestBody Request<RecetteDto> request) throws Exception{
    	log.info("start method /recette/delete");
        Response<RecetteDto> response = controllerFactory.delete(recetteBusiness, request, FunctionalityEnum.DELETE_RECETTE);
		log.info("end method /recette/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<RecetteDto> getByCriteria(@RequestBody Request<RecetteDto> request) throws Exception{
    	log.info("start method /recette/getByCriteria");
        Response<RecetteDto> response = controllerFactory.getByCriteria(recetteBusiness, request, FunctionalityEnum.VIEW_RECETTE);
		log.info("end method /recette/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<RecetteDto> custom(@RequestBody Request<RecetteDto> request) throws Exception{
	  log.info("RecetteDto method /$/custom");
	  Response<RecetteDto> response = new Response<RecetteDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = recetteBusiness.custom(request, locale);
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
	  slf4jLogger.info("end method /RecetteDto/custom");
	  return response;
	 }
}
