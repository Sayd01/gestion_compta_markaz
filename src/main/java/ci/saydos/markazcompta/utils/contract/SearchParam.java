
/*
 * Created on 2023-08-06 ( Time 01:31:16 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils.contract;

import lombok.*;

/**
 * Search Param
 * 
 * @author Geo
 *
 */
@Data
@ToString
@NoArgsConstructor
public class SearchParam<T> {

	String	operator;
	T		start;
	T		end;
}
