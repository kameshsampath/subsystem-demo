package org.workspace7.osgi.subsystem.helper.consumer.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workspace7.osgi.subsystem.helper.api.Helper;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {

		_bundleContext = context;

		_helperServiceRef = context.getServiceReference(Helper.class);

		Helper helper = context.getService(_helperServiceRef);

		if (helper != null) {
			helper.message("Hello from a consumer outside the subsystem");
		} else {
			_log.error("No Helper available");
		}

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		_bundleContext.ungetService(_helperServiceRef);

	}

	private Logger _log = LoggerFactory.getLogger(Activator.class);
	private BundleContext _bundleContext;
	private ServiceReference<Helper> _helperServiceRef;

}
