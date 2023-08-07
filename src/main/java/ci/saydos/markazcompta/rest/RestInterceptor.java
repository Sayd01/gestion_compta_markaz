/*
 * Created on 2023-05-28 ( Time 13:24:27 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2017 Savoir Faire Linux. All Rights Reserved.
 */

 package ci.saydos.markazcompta.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;


public class RestInterceptor implements HandlerInterceptor {

	private static String	defaultTenant	= "null";
	
	private static String defaultLanguage = "fr";

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {

		String tenantValue = req.getHeader("tenantID");

		if (tenantValue != null) {
			req.setAttribute("CURRENT_TENANT_IDENTIFIER", tenantValue);
		} else {
			req.setAttribute("CURRENT_TENANT_IDENTIFIER", defaultTenant);
		}
		
		String langValue = req.getHeader("lang");

		if (langValue != null) {
			req.setAttribute("CURRENT_LANGUAGE_IDENTIFIER", langValue);
		} else {
			req.setAttribute("CURRENT_LANGUAGE_IDENTIFIER", defaultLanguage);
		}
		return true;
	}
}