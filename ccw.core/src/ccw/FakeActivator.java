package ccw;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

public class FakeActivator implements BundleActivator {

	public void start(BundleContext context) throws Exception {
		System.out.println("CCW's FakeActivator called");
		for (final Bundle b: context.getBundles()) {
			if (b.getSymbolicName().equals("org.clojure.tools.nrepl")) {
				new Thread(new Runnable() {
					public void run() {
						try {
							System.out.println("Found nrepl, starting the bundle");
							b.start();
						} catch (BundleException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
		for (final Bundle b: context.getBundles()) {
			if (b.getSymbolicName().equals("clojure.osgi")) {
				new Thread(new Runnable() {
					public void run() {
						try {
							System.out.println("Found clojure.osgi, starting the bundle");
							b.start();
						} catch (BundleException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
		System.out.println("exiting fake activator");
	}

	public void stop(BundleContext context) throws Exception {
	}

}
