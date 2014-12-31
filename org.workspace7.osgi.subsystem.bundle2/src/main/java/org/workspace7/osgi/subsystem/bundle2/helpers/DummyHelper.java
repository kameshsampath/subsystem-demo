package org.workspace7.osgi.subsystem.bundle2.helpers;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.workspace7.osgi.subsystem.helper.api.Helper;

@Component(name = "org.workspace7.osgi.subsystem.bundle2.helpers.DummyHelper", service = Helper.class, immediate = true)
public class DummyHelper implements Helper {

	public DummyHelper() {
		_log.info("[OSGi Subsystem]: Dummy Helper ");
	}

	// very complex logic be careful :)
	public void message(String text) {
		_log.info("[OSGi Subsystem]:" + text);
	}

	private Logger _log = LoggerFactory.getLogger(DummyHelper.class);
}
