
/*
 * Created on 2023-08-28 ( Time 13:26:33 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

package ci.saydos.markazcompta.rest.fact;


import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;

import java.util.Locale;
import jakarta.servlet.http.HttpServletRequest;

import ci.saydos.markazcompta.utils.contract.IController;
import ci.saydos.markazcompta.utils.contract.IBasicBusiness;
import ci.saydos.markazcompta.utils.contract.Request;
import ci.saydos.markazcompta.utils.contract.Response;
import ci.saydos.markazcompta.utils.enums.FunctionalityEnum;
import ci.saydos.markazcompta.utils.ExceptionUtils;
import ci.saydos.markazcompta.utils.FunctionalError;
import ci.saydos.markazcompta.utils.StatusCode;
import ci.saydos.markazcompta.utils.StatusMessage;
import ci.saydos.markazcompta.utils.Validate;
import ci.saydos.markazcompta.business.fact.BusinessFactory;

@Log
@Component
public class ControllerFactory<DTO> implements IController<DTO> {
    @Autowired
    private BusinessFactory<DTO> businessFactory;
    @Autowired
    private FunctionalError      functionalError;
    @Autowired
    private ExceptionUtils       exceptionUtils;
    @Autowired
    private HttpServletRequest   requestBasic;

    @Override
    public Response<DTO> create(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum) throws Exception {
        Response<DTO> response   = new Response<DTO>();
        String        languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale        locale     = new Locale(languageID, "");
        try {
            response = Validate.validateList(request, response, functionalError, locale);
            if (!response.isHasError()) {
                response = businessFactory.create(iBasicBusiness, request, functionalityEnum, locale);
            } else {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                return response;
            }
            if (!response.isHasError()) {
                log.info(String.format("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS));
            } else {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
            }
        } catch (CannotCreateTransactionException e) {
            exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
        } catch (TransactionSystemException e) {
            exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
        } 
        return response;
    }

    @Override
    public Response<DTO> update(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum) throws Exception {
        Response<DTO> response   = new Response<DTO>();
        String        languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale        locale     = new Locale(languageID, "");
        try {
            response = Validate.validateList(request, response, functionalError, locale);
            if (!response.isHasError()) {
                response = businessFactory.update(iBasicBusiness, request, functionalityEnum, locale);
            } else {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                return response;
            }
            if (!response.isHasError()) {
                log.info(String.format("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS));
            } else {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
            }
        } catch (CannotCreateTransactionException e) {
            exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
        } catch (TransactionSystemException e) {
            exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
        } 
        return response;
    }

    @Override
    public Response<DTO> delete(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum) throws Exception {
        Response<DTO> response   = new Response<DTO>();
        String        languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale        locale     = new Locale(languageID, "");
        try {
            response = Validate.validateList(request, response, functionalError, locale);
            if (!response.isHasError()) {
                response = businessFactory.delete(iBasicBusiness, request, functionalityEnum, locale);
            } else {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                return response;
            }
            if (!response.isHasError()) {
                log.info(String.format("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS));
            } else {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
            }
        } catch (CannotCreateTransactionException e) {
            exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
        } catch (TransactionSystemException e) {
            exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
        } 
        return response;
    }

    @Override
    public Response<DTO> getByCriteria(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum) throws Exception {
        Response<DTO> response   = new Response<DTO>();
        String        languageID = (String) requestBasic.getAttribute("CURRENT_LANGUAGE_IDENTIFIER");
        Locale        locale     = new Locale(languageID, "");
        try {
            response = Validate.validateObject(request, response, functionalError, locale);
            if (!response.isHasError()) {
                response = businessFactory.getByCriteria(iBasicBusiness, request, functionalityEnum, locale);
            } else {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                return response;
            }
            if (!response.isHasError()) {
                log.info(String.format("code: {} -  message: {}", StatusCode.SUCCESS, StatusMessage.SUCCESS));
            } else {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
            }
        } catch (CannotCreateTransactionException e) {
            exceptionUtils.CANNOT_CREATE_TRANSACTION_EXCEPTION(response, locale, e);
        } catch (TransactionSystemException e) {
            exceptionUtils.TRANSACTION_SYSTEM_EXCEPTION(response, locale, e);
        } 
        return response;
    }
}
