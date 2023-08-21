/*
 * Created on 2023-08-08 ( Time 19:03:09 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils.contract;

import lombok.*;

/**
 * Request Base
 * 
 * @author Geo
 *
 */
@Data
@ToString
@NoArgsConstructor
public class RequestBase {
	protected String		sessionUser;
	protected Integer		size;
	protected Integer		index;
	protected String		lang;
	protected String		businessLineCode;
	protected String		caseEngine;
	protected Boolean		isAnd;
	protected Integer		user;
	protected Boolean 		isSimpleLoading;
}