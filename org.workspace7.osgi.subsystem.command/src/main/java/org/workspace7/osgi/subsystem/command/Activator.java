package org.workspace7.osgi.subsystem.command;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

import java.net.URL;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.subsystem.Subsystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context)
		throws Exception {
		
		try {
			_bundleContext = context;
			
			Dictionary<String, Object> serviceProperties = 
							new Hashtable<String, Object>();
			
			serviceProperties.put("osgi.command.function"
				, new String[]{"list","install","stop","start","uninstall"});
			serviceProperties.put("osgi.command.scope", "subsystem");
			
			context.registerService(getClass().getName(), this, serviceProperties);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public void install(String url) throws Exception{
		//get root subsystem
		
		try {
			Subsystem rootSubsystem = getSubsystem(0);
			
			Subsystem installedSubsystem = rootSubsystem.install(url,
				new URL(url).openStream());
			
			System.out.println("Subsystem successfully installed:"+
			installedSubsystem.getSymbolicName()+", id:"
							+ installedSubsystem.getSubsystemId());
		}
		catch (Exception e) {
			_log.error("Error installing subsystem:", e);
		}
		
		
	}
	
	public void list() throws InvalidSyntaxException{
		for (ServiceReference<Subsystem> ref :
			_bundleContext.getServiceReferences(Subsystem.class, null)) {
			Subsystem s = _bundleContext.getService(ref);
			if (s != null) {
				System.out.printf("%d\t%s\t%s\n", 
					s.getSubsystemId(), s.getState(), s.getSymbolicName());
			}
		}
	}
	
	public void start(long id) throws Exception{
		try {
			getSubsystem(id).start();
		}
		catch (Exception e) {
			_log.error("Error starting subsystem:", e);
		}
	}
	
	public void stop(long id) throws Exception{
		try {
			getSubsystem(id).stop();
		}
		catch (Exception e) {
			_log.error("Error stopping subsystem:", e);
		}
	}
	
	public void uninstall(long id) throws Exception{
		try {
			getSubsystem(id).uninstall();
		}
		catch (Exception e) {
			_log.error("Error uninstalling subsystem:", e);
		}

	}

	@Override
	public void stop(BundleContext context){
		
	}

	@SuppressWarnings(value={"unchecked","rawtypes"})
	private Subsystem getSubsystem(long id) throws Exception{
	
		try {
			Collection serviceRefs =
							_bundleContext.getServiceReferences(
								Subsystem.class, "(subsystem.id="+id+")");

			for (Iterator iterator = serviceRefs.iterator(); iterator.hasNext();) {

				ServiceReference<Subsystem> serviceReference =
								(ServiceReference<Subsystem>) iterator.next();

				Subsystem subsystem = _bundleContext.getService(
					serviceReference);		

				if(subsystem!=null){
					
					return subsystem;
				}
			}
		}
		catch (Exception e) {
			_log.error("Error getting subsystem:", e);
		}
			
		return null;
	}
	
	private Logger _log = LoggerFactory.getLogger(Activator.class);
	private BundleContext _bundleContext;	
	
}
