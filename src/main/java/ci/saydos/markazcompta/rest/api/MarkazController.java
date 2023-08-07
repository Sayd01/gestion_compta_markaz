

/*
 * Java controller for entity table markaz 
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
Controller for table "markaz"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/markaz")
public class MarkazController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<MarkazDto> controllerFactory;
	@Autowired
	private MarkazBusiness markazBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public ResponseEntity<Response<MarkazDto>> create(@RequestBody Request<MarkazDto> request) throws Exception {
    	log.info("start method /markaz/create");
        Response<MarkazDto> response = controllerFactory.create(markazBusiness, request, FunctionalityEnum.CREATE_MARKAZ);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.buildAndExpand(response.getItems().get(0))
				.toUri();
		log.info("end method /markaz/create");
        return ResponseEntity.created(uri).body(response);
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<MarkazDto> update(@RequestBody Request<MarkazDto> request) throws Exception{
    	log.info("start method /markaz/update");
        Response<MarkazDto> response = controllerFactory.update(markazBusiness, request, FunctionalityEnum.UPDATE_MARKAZ);
		log.info("end method /markaz/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<MarkazDto> delete(@RequestBody Request<MarkazDto> request) throws Exception{
    	log.info("start method /markaz/delete");
        Response<MarkazDto> response = controllerFactory.delete(markazBusiness, request, FunctionalityEnum.DELETE_MARKAZ);
		log.info("end method /markaz/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<MarkazDto> getByCriteria(@RequestBody Request<MarkazDto> request) throws Exception{
    	log.info("start method /markaz/getByCriteria");
        Response<MarkazDto> response = controllerFactory.getByCriteria(markazBusiness, request, FunctionalityEnum.VIEW_MARKAZ);
		log.info("end method /markaz/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<MarkazDto> custom(@RequestBody Request<MarkazDto> request) throws Exception{
	  log.info("MarkazDto method /$/custom");
	  Response<MarkazDto> response = new Response<MarkazDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = markazBusiness.custom(request, locale);
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
	  slf4jLogger.info("end method /MarkazDto/custom");
	  return response;
	 }
}
