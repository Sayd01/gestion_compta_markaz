

/*
 * Java controller for entity table stock_historique 
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
Controller for table "stock_historique"
 * 
 * @author SFL Back-End developper
 *
 */
@Log
@CrossOrigin("*")
@RestController
@RequestMapping(value="/stockHistorique")
public class StockHistoriqueController {

     @Autowired
	 private HttpServletRequest requestBasic;
	 @Autowired
	 private FunctionalError functionalError;
	 @Autowired
	 private ExceptionUtils exceptionUtils;

	 private Logger slf4jLogger = LoggerFactory.getLogger(getClass());

	@Autowired
    private ControllerFactory<StockHistoriqueDto> controllerFactory;
	@Autowired
	private StockHistoriqueBusiness stockHistoriqueBusiness;

	@RequestMapping(value="/create",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public ResponseEntity<Response<StockHistoriqueDto>> create(@RequestBody Request<StockHistoriqueDto> request) throws Exception {
    	log.info("start method /stockHistorique/create");
        Response<StockHistoriqueDto> response = controllerFactory.create(stockHistoriqueBusiness, request, FunctionalityEnum.CREATE_STOCK_HISTORIQUE);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.buildAndExpand(response.getItems().get(0))
				.toUri();
		log.info("end method /stockHistorique/create");
        return ResponseEntity.created(uri).body(response);
    }

	@RequestMapping(value="/update",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<StockHistoriqueDto> update(@RequestBody Request<StockHistoriqueDto> request) throws Exception{
    	log.info("start method /stockHistorique/update");
        Response<StockHistoriqueDto> response = controllerFactory.update(stockHistoriqueBusiness, request, FunctionalityEnum.UPDATE_STOCK_HISTORIQUE);
		log.info("end method /stockHistorique/update");
        return response;
    }

	@RequestMapping(value="/delete",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<StockHistoriqueDto> delete(@RequestBody Request<StockHistoriqueDto> request) throws Exception{
    	log.info("start method /stockHistorique/delete");
        Response<StockHistoriqueDto> response = controllerFactory.delete(stockHistoriqueBusiness, request, FunctionalityEnum.DELETE_STOCK_HISTORIQUE);
		log.info("end method /stockHistorique/delete");
        return response;
    }

	@RequestMapping(value="/getByCriteria",method=RequestMethod.POST,consumes = {"application/json"},produces={"application/json"})
    public Response<StockHistoriqueDto> getByCriteria(@RequestBody Request<StockHistoriqueDto> request) throws Exception{
    	log.info("start method /stockHistorique/getByCriteria");
        Response<StockHistoriqueDto> response = controllerFactory.getByCriteria(stockHistoriqueBusiness, request, FunctionalityEnum.VIEW_STOCK_HISTORIQUE);
		log.info("end method /stockHistorique/getByCriteria");
        return response;
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = {
	   "application/json" }, produces = { "application/json" })
	 public Response<StockHistoriqueDto> custom(@RequestBody Request<StockHistoriqueDto> request) throws Exception{
	  log.info("StockHistoriqueDto method /$/custom");
	  Response<StockHistoriqueDto> response = new Response<StockHistoriqueDto>();
	  String languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
	  Locale locale = new Locale(languageID, "");
	  try {
	   response = Validate.validateList(request, response, functionalError, locale);
	   if (!response.isHasError()) {
	    response = stockHistoriqueBusiness.custom(request, locale);
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
	  slf4jLogger.info("end method /StockHistoriqueDto/custom");
	  return response;
	 }
}
