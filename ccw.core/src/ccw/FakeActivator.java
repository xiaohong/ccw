package ccw;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.util.tracker.BundleTracker;

public class FakeActivator implements BundleActivator {

	public void start(BundleContext context) throws Exception {
		new BundleTracker(context, Bundle.RESOLVED, null) {
			@Override
			public Object addingBundle(Bundle bundle, BundleEvent event) {
				if(bundle.getSymbolicName().equals("clojure.osgi")) {
					try {
						bundle.start();
					}catch(BundleException e) {
						e.printStackTrace();
					}
					
					close();
				}
				
				return super.addingBundle(bundle, event);
			}
		}.open();
		
		System.out.println("exiting fake activator");
	}

	public void stop(BundleContext context) throws Exception {
	}

}
