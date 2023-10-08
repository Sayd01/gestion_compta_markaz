

/*
 * Java controller for entity table depense 
 * Created on 2023-08-09 ( Time 18:01:30 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

package ci.saydos.markazcompta.rest.api;

import lombok.RequiredArgsConstructor;
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
Controller for table "depense"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/depense")
@RequiredArgsConstructor
public class DepenseController {

	 private final HttpServletRequest requestBasic;
	 private final FunctionalError functionalError;
	 private final ExceptionUtils exceptionUtils;
	 private final Logger slf4jLogger = LoggerFactory.getLogger(getClass());
    private final ControllerFactory<DepenseDto> controllerFactory;
	private final DepenseBusiness depenseBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public ResponseEntity<Response<DepenseDto>> create(@RequestBody Request<DepenseDto> request) throws Exception {
    	log.info("start method /depense/create");
        Response<DepenseDto> response = controllerFactory.create(depenseBusiness, request, FunctionalityEnum.CREATE_DEPENSE);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.buildAndExpand(response.getItems().get(0))
				.toUri();
		log.info("end method /depense/create");
        return ResponseEntity.created(uri).body(response);
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<DepenseDto> update(@RequestBody Request<DepenseDto> request) throws Exception{
    	log.info("start method /depense/update");
        Response<DepenseDto> response = controllerFactory.update(depenseBusiness, request, FunctionalityEnum.UPDATE_DEPENSE);
		log.info("end method /depense/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<DepenseDto> delete(@RequestBody Request<DepenseDto> request) throws Exception{
    	log.info("start method /depense/delete");
        Response<DepenseDto> response = controllerFactory.delete(depenseBusiness, request, FunctionalityEnum.DELETE_DEPENSE);
		log.info("end method /depense/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<DepenseDto> getByCriteria(@RequestBody Request<DepenseDto> request) throws Exception{
    	log.info("start method /depense/getByCriteria");
        Response<DepenseDto> response = controllerFactory.getByCriteria(depenseBusiness, request, FunctionalityEnum.VIEW_DEPENSE);
		log.info("end method /depense/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<DepenseDto> custom(@RequestBody Request<DepenseDto> request) throws Exception{
	  log.info("DepenseDto method /$/custom");
	  Response<DepenseDto> response = new Response<DepenseDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = depenseBusiness.custom(request, locale);
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
	  slf4jLogger.info("end method /DepenseDto/custom");
	  return response;
	 }

	@RequestMapping(value = "/createdDepenseFixe", method = RequestMethod.POST, consumes = {
			"application/json"}, produces = {"application/json"})
	public Response<DepenseDto> fixe(@RequestBody Request<DepenseDto> request) throws Exception {
		log.info("start method /Depense/fixe");
		Response<DepenseDto> response   = new Response<>();
		String               languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale               locale     = new Locale(languageID, "");
		try {
			response = Validate.validateList(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = depenseBusiness.createdDepenseFixe(request, locale);
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
		log.info("end method /Depense/fixe");
		return response;
	}

	@RequestMapping(value = "/variable", method = RequestMethod.POST, consumes = {
			"application/json"}, produces = {"application/json"})
	public Response<DepenseDto> variable(@RequestBody Request<DepenseDto> request) throws Exception {
		log.info("start method /Depense/variable");
		Response<DepenseDto> response   = new Response<>();
		String               languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale               locale     = new Locale(languageID, "");
		try {
			response = Validate.validateList(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = depenseBusiness.paidDepenseVarible(request, locale);
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
		log.info("end method /Depense/variable");
		return response;
	}

	@RequestMapping(value = "/paidDepenseFixe", method = RequestMethod.POST, consumes = {
			"application/json"}, produces = {"application/json"})
	public Response<DepenseDto> paidDepenseFixe(@RequestBody Request<DepenseDto> request) throws Exception {
		log.info("start method /Depense/paidDepenseFixe");
		Response<DepenseDto> response   = new Response<>();
		String               languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
		Locale               locale     = new Locale(languageID, "");
		try {
			response = Validate.validateList(request, response, functionalError, locale);
			if (!response.isHasError()) {
				response = depenseBusiness.paidDepenseFixe(request, locale);
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
		log.info("end method /Depense/paidDepenseFixe");
		return response;
	}
}
