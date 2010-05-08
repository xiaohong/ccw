package ccw.console;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.TextConsole;
import org.eclipse.ui.internal.console.IOConsolePage;

public class ClojureConsolePage extends IOConsolePage {
	private IPreferenceStore store;
	
	public ClojureConsolePage(TextConsole console, IConsoleView view, IPreferenceStore store) {
		super(console, view);
		this.store = store;
	}

	@Override
	protected ClojureConsoleViewer createViewer(Composite parent) {
		return new ClojureConsoleViewer(parent, (TextConsole)getConsole(), store);
	}
}
