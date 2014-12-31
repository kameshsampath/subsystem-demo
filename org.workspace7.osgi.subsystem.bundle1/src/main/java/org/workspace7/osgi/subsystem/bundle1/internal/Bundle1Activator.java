package org.workspace7.osgi.subsystem.bundle1.internal;

import javax.servlet.ServletException;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workspace7.osgi.subsystem.helper.api.Helper;

public class Bundle1Activator implements BundleActivator {

	public void start(BundleContext bundleContext) throws Exception {

		_log.trace("Bundle1Activator.start()");

		_bundleContext = bundleContext;

		HttpServiceTracker httpServiceTrackerCustomizer = new HttpServiceTracker();

		_httpServiceTracker = new ServiceTracker<HttpService, HttpService>(
				bundleContext, HttpService.class, httpServiceTrackerCustomizer);

		_httpServiceTracker.open();

		HelperServiceTracker helperServiceTracker = new HelperServiceTracker();

		_helperServiceTracker = new ServiceTracker<Helper, Helper>(
				bundleContext, Helper.class, helperServiceTracker);

		_helperServiceTracker.open();

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		_httpServiceTracker.close();
		_helperServiceTracker.close();
	}

	private Logger _log = LoggerFactory.getLogger(Bundle1Activator.class);
	private BundleContext _bundleContext;
	private ServiceTracker<HttpService, HttpService> _httpServiceTracker;
	private ServiceTracker<Helper, Helper> _helperServiceTracker;
	private Helper _helper;
	private HttpService _httpService;

	private class HelperServiceTracker implements
			ServiceTrackerCustomizer<Helper, Helper> {

		@Override
		public Helper addingService(ServiceReference<Helper> reference) {
		
			_log.debug("Bundle1Activator.HelperServiceTracker.addingService()");
			
			_helper = _bundleContext.getService(reference);

			if (_helper != null && _httpService != null) {

				try {

					_log.debug("Registering servlet /bundle1");

					_httpService.registerServlet("/bundle1",
							new BundleServlet1(_helper), null, null);

				} catch (ServletException e) {
					e.printStackTrace();
				} catch (NamespaceException e) {
					e.printStackTrace();
				}
			} else {
				_log.error("No Helper available");
			}

			return _helper;
		}

		@Override
		public void modifiedService(ServiceReference<Helper> reference,
				Helper service) {
			removedService(reference, service);
			addingService(reference);
		}

		@Override
		public void removedService(ServiceReference<Helper> reference,
				Helper service) {
			_helper = null;

		}

	}

	private class HttpServiceTracker implements
			ServiceTrackerCustomizer<HttpService, HttpService> {

		@Override
		public HttpService addingService(ServiceReference<HttpService> reference) {
			_httpService = _bundleContext.getService(reference);
			return _httpService;
		}

		@Override
		public void modifiedService(ServiceReference<HttpService> reference,
				HttpService service) {
			removedService(reference, service);
			addingService(reference);

		}

		@Override
		public void removedService(ServiceReference<HttpService> reference,
				HttpService service) {
			_httpService = null;

		}

	}

}
