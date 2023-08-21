

/*
 * Created on 2023-08-08 ( Time 19:03:09 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

package ci.saydos.markazcompta.business.fact;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;   

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ci.saydos.markazcompta.utils.exception.*;
import ci.saydos.markazcompta.utils.*;
import ci.saydos.markazcompta.utils.ExceptionUtils;
import ci.saydos.markazcompta.utils.FunctionalError;
import ci.saydos.markazcompta.utils.Validate;
import ci.saydos.markazcompta.utils.Utilities;
import ci.saydos.markazcompta.utils.contract.IBasicBusiness;
import ci.saydos.markazcompta.utils.contract.Request;
import ci.saydos.markazcompta.utils.contract.Response;
import ci.saydos.markazcompta.utils.enums.FunctionalityEnum;
import ci.saydos.markazcompta.business.fact.BusinessFactory;


/**
 * BUSINESS factory
 *
 * @author Geo
 */
@Log
@Component
public class BusinessFactory<DTO> {

    @Autowired
    private FunctionalError functionalError;
    @Autowired
    private ExceptionUtils  exceptionUtils;

    /**
     * create entity by using dto as object.
     *
     * @param request
     * @return response
     */
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Response<DTO> create(IBasicBusiness<Request<DTO>, Response<DTO>> iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum, Locale locale) throws ParseException{
        Response<DTO> response = new Response<DTO>();
        try {
            checkUserAccess(request, functionalityEnum, locale);
            response = iBasicBusiness.create(request, locale);
        } catch (PermissionDeniedDataAccessException e) {
            exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (DataAccessResourceFailureException e) {
            exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
        } catch (DataAccessException e) {
            exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
        } finally {
            if (response.isHasError() && response.getStatus() != null) {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                Status status  = new Status();
                status.setMessage(response.getStatus().getMessage());
                status.setCode(response.getStatus().getCode());
                throw new InternalErrorException(status);
            }
        }
        return response;
    }

    /**
     * update entity by using dto as object.
     *
     * @param request
     * @return response
     */
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Response<DTO> update(IBasicBusiness<Request<DTO>, Response<DTO>> iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum, Locale locale) throws ParseException {
        Response<DTO> response = new Response<DTO>();
        try {
            checkUserAccess(request, functionalityEnum, locale);
            response = iBasicBusiness.update(request, locale);
        } catch (PermissionDeniedDataAccessException e) {
            exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (DataAccessResourceFailureException e) {
            exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
        } catch (DataAccessException e) {
            exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
        } finally {
            if (response.isHasError() && response.getStatus() != null) {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                Status status  = new Status();
                status.setMessage(response.getStatus().getMessage());
                status.setCode(response.getStatus().getCode());
                throw new InternalErrorException(status);
            }
        }
        return response;
    }

    /**
     * delete entity by using dto as object.
     *
     * @param request
     * @return response
     */
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Response<DTO> delete(IBasicBusiness<Request<DTO>, Response<DTO>> iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum, Locale locale) throws ParseException{
        Response<DTO> response = new Response<DTO>();
        try {
            checkUserAccess(request, functionalityEnum, locale);
            response = iBasicBusiness.delete(request, locale);
        } catch (PermissionDeniedDataAccessException e) {
            exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (DataAccessResourceFailureException e) {
            exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
        } catch (DataAccessException e) {
            exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
        } finally {
            if (response.isHasError() && response.getStatus() != null) {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                Status status  = new Status();
                status.setMessage(response.getStatus().getMessage());
                status.setCode(response.getStatus().getCode());
                throw new InternalErrorException(status);            
            }
        }
        return response;
    }

    /**
     * delete entity by using dto as object.
     *
     * @param request
     * @return response
     */
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Response<DTO> getByCriteria(IBasicBusiness<Request<DTO>, Response<DTO>> iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum, Locale locale) throws Exception{
        Response<DTO> response = new Response<DTO>();
        try {
            checkUserAccess(request, functionalityEnum, locale);
            response = iBasicBusiness.getByCriteria(request, locale);
        } catch (PermissionDeniedDataAccessException e) {
            exceptionUtils.PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(response, locale, e);
        } catch (DataAccessResourceFailureException e) {
            exceptionUtils.DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(response, locale, e);
        } catch (DataAccessException e) {
            exceptionUtils.DATA_ACCESS_EXCEPTION(response, locale, e);
        } finally {
            if (response.isHasError() && response.getStatus() != null) {
                log.info(String.format("Erreur| code: {} -  message: {}", response.getStatus().getCode(), response.getStatus().getMessage()));
                Status status  = new Status();
                status.setMessage(response.getStatus().getMessage());
                status.setCode(response.getStatus().getCode());
                throw new InternalErrorException(status);
            }
        }
        return response;
    }

    private void checkUserAccess(Request<DTO> request, FunctionalityEnum functionalityEnum, Locale locale) {
        Map<String, java.lang.Object> fieldsToVerifyUser = new HashMap<String, java.lang.Object>();
        fieldsToVerifyUser.put("user", request.getUser());
        if (!Validate.RequiredValue(fieldsToVerifyUser).isGood()) {
            throw new InvalidEntityException(functionalError.FIELD_EMPTY(Validate.getValidate().getField(), locale));
        }
    }


}
