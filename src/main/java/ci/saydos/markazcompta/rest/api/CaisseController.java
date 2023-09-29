

/*
 * Java controller for entity table caisse 
 * Created on 2023-08-10 ( Time 17:44:55 )
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
Controller for table "caisse"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/caisse")
public class CaisseController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<CaisseDto> controllerFactory;
	@Autowired
	private CaisseBusiness caisseBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public ResponseEntity<Response<CaisseDto>> create(@RequestBody Request<CaisseDto> request) throws Exception {
    	log.info("start method /caisse/create");
        Response<CaisseDto> response = controllerFactory.create(caisseBusiness, request, FunctionalityEnum.CREATE_CAISSE);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.buildAndExpand(response.getItems().get(0))
				.toUri();
		log.info("end method /caisse/create");
        return ResponseEntity.created(uri).body(response);
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<CaisseDto> update(@RequestBody Request<CaisseDto> request) throws Exception{
    	log.info("start method /caisse/update");
        Response<CaisseDto> response = controllerFactory.update(caisseBusiness, request, FunctionalityEnum.UPDATE_CAISSE);
		log.info("end method /caisse/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<CaisseDto> delete(@RequestBody Request<CaisseDto> request) throws Exception{
    	log.info("start method /caisse/delete");
        Response<CaisseDto> response = controllerFactory.delete(caisseBusiness, request, FunctionalityEnum.DELETE_CAISSE);
		log.info("end method /caisse/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<CaisseDto> getByCriteria(@RequestBody Request<CaisseDto> request) throws Exception{
    	log.info("start method /caisse/getByCriteria");
        Response<CaisseDto> response = controllerFactory.getByCriteria(caisseBusiness, request, FunctionalityEnum.VIEW_CAISSE);
		log.info("end method /caisse/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<CaisseDto> custom(@RequestBody Request<CaisseDto> request) throws Exception{
	  log.info("CaisseDto method /$/custom");
	  Response<CaisseDto> response = new Response<CaisseDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = caisseBusiness.custom(request, locale);
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
	  slf4jLogger.info("end method /CaisseDto/custom");
	  return response;
	 }

	@RequestMapping(value = "/approvisionnement", method = RequestMethod.POST, consumes = {
			"application/json"}, produces = {"application/json"})
	public Response<CaisseDto> variable(@RequestBody Request<CaisseDto> request) throws Exception {
		log.info("start method /Caisse/approvisionnement");
		Response<CaisseDto> response   = new Response<>();
		String               languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale               locale     = new Locale(languageID, "");
		try {
			response = Validate.validateList(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = caisseBusiness.approvisionnement(request, locale);
			} else {
				slf4jLogger.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
				return response;
			}
			if (!response.isHasError()) {
				slf4jLogger.info(String.format("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS));
			} else {
				slf4jLogger.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
			}
		} catch (CannotCreateTransactionException e) {
			exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
		} catch (TransactionSystemException e) {
			exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
		}
		log.info("end method /Caisse/approvisionnement");
		return response;
	}
}
