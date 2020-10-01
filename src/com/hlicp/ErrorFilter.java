package com.hlicp;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class ErrorFilter extends ZuulFilter {

	@Override
	public String filterType() {
		return "error";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return RequestContext.getCurrentContext().getThrowable() != null;
	}

	@Override
	public Object run() {
		Throwable throwable = RequestContext.getCurrentContext().getThrowable();
		return null;
	}
}
