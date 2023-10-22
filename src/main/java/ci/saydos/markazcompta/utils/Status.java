
/*
 * Created on 2023-08-28 ( Time 13:26:33 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2018 Geo. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

/**
 * Status
 * 
 * @author Geo
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Status {
	private String	code;
	private String	message;

}
