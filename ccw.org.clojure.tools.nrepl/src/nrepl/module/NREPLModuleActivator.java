package nrepl.module;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import clojure.osgi.ClojureOSGi;

public class NREPLModuleActivator implements BundleActivator {
//	private Bundle bundle;

	public static final String PLUGIN_ID = "nrepl.module";

	public void start(BundleContext context) throws Exception {
//		bundle = context.getBundle();
		startClojureCode(context);
	}

	private void startClojureCode(BundleContext bundleContext) throws Exception {
//		ClojureOSGi.loadAOTClass(bundleContext, "ccw.ClojureProjectNature");
		ClojureOSGi.require(bundleContext, "nrepl.module.core");
	}

	public void stop(BundleContext context) throws Exception {
//		bundle = null;
	}

}