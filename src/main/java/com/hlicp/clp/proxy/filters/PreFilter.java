package com.hlicp.clp.proxy.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class PreFilter extends ZuulFilter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Object run() {
		logger.debug("prefilter started, ");

		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		logger.debug("Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString());

		HttpSession session = request.getSession();
		Object authObj = session.getAttribute("authorized");
		if (authObj == null || !(boolean) authObj) {
			System.err.println("The request is not authorized.");
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
			ctx.setResponseBody("Unauthorized");
		}

		logger.debug("prefilter ended.");

		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

}