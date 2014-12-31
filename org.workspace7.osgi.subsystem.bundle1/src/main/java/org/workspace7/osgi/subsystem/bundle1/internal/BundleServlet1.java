/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package org.workspace7.osgi.subsystem.bundle1.internal;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workspace7.osgi.subsystem.helper.api.Helper;

/**
 * @author Kamesh Sampath
 *
 */
public class BundleServlet1 extends GenericServlet {

	public BundleServlet1(Helper helper) {
		_log.debug("BundleServlet1.BundleServlet1()");
		_helper = helper;
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		_helper.message("Hello form Subsystem Bundle1");
		PrintWriter printWriter = response.getWriter();
		printWriter.write("Hello form Subsystem Bundle1");
	}

	private Logger _log = LoggerFactory.getLogger(BundleServlet1.class);
	private Helper _helper;
	
}
