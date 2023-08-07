
/*
 * Created on 2023-08-06 ( Time 01:31:16 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils.contract;

import ci.saydos.markazcompta.utils.enums.*;

/**
 * IController
 * 
 * @author Geo
 *
 */
public interface IController<DTO> {
    Response<DTO> create(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum) throws Exception;

    Response<DTO> update(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum) throws Exception;

    Response<DTO> delete(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum) throws Exception;

    Response<DTO> getByCriteria(IBasicBusiness iBasicBusiness, Request<DTO> request, FunctionalityEnum functionalityEnum) throws Exception;
}

