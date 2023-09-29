
/*
 * Created on 2023-08-28 ( Time 13:26:33 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils.contract;

import lombok.*;
import ci.saydos.markazcompta.utils.Status;

/**
 * Response Base
 * 
 * @author Geo
 *
 */
@Data
@ToString
@NoArgsConstructor
public class ResponseBase {

	protected  Status	 status = new Status();
	protected  boolean	 hasError;
	protected  String	 sessionUser;
	protected  Long		 count;
	protected  int       httpCode;
}
