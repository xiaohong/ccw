package ccw.console;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.TextConsole;
import org.eclipse.ui.internal.console.IOConsoleViewer;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.AbstractTextEditor;

import ccw.CCWPlugin;
import ccw.editors.antlrbased.ClojureSourceViewerConfiguration;

public class ClojureConsoleViewer extends IOConsoleViewer implements IPropertyChangeListener {
	private ClojureSourceViewerConfiguration fConfiguration;
	
	/** The preference store. */
    private IPreferenceStore fPreferenceStore;
    
	/**
	 * This viewer's foreground color.
	 */
	private Color fForegroundColor;
	
	/**
	 * The viewer's background color.
	 */
	private Color fBackgroundColor;
	
	/**
	 * This viewer's selection foreground color.
	 */
	private Color fSelectionForegroundColor;
	
	/**
	 * The viewer's selection background color.
	 */
	private Color fSelectionBackgroundColor;

	private boolean fIsConfigured;
	
	public ClojureConsoleViewer(Composite parent, TextConsole console, IPreferenceStore store) {
		super(parent, console);
		setPreferenceStore(store);
		configure(new ClojureSourceViewerConfiguration(store, null));
	}
	
    public void configure(SourceViewerConfiguration configuration) {
        super.configure(configuration);

        if (fPreferenceStore != null) {
            fPreferenceStore.addPropertyChangeListener(this);
            initializeViewerColors();
        }
        
        if (configuration instanceof ClojureSourceViewerConfiguration)
            fConfiguration = (ClojureSourceViewerConfiguration) configuration;

        fIsConfigured= true;
    }

    /**
     * Sets the preference store on this viewer.
     *
     * @param store the preference store
     *
     * @since 3.0
     */
    public void setPreferenceStore(IPreferenceStore store) {
        if (fIsConfigured && fPreferenceStore != null)
            fPreferenceStore.removePropertyChangeListener(this);

        fPreferenceStore= store;

        if (fIsConfigured && fPreferenceStore != null) {
            fPreferenceStore.addPropertyChangeListener(this);
            initializeViewerColors();
        }
    }
    
    public void propertyChange(PropertyChangeEvent event) {
        if (fConfiguration != null) {
            ClojureSourceViewerConfiguration tmp = fConfiguration;
            unconfigure();
            initializeViewerColors();
            tmp.initTokenScanner();
            configure(tmp);
        }
    }
    
    public void initializeViewerColors() {
		if (fPreferenceStore != null) {
			StyledText styledText= getTextWidget();

			// ----------- foreground color --------------------
			Color color= fPreferenceStore.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND_SYSTEM_DEFAULT)
			? null
			: createColor(fPreferenceStore, AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND, styledText.getDisplay());
			styledText.setForeground(color);

			if (fForegroundColor != null)
				fForegroundColor.dispose();

			fForegroundColor= color;

			// ---------- background color ----------------------
			color= fPreferenceStore.getBoolean(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND_SYSTEM_DEFAULT)
			? null
			: createColor(fPreferenceStore, AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND, styledText.getDisplay());
			styledText.setBackground(color);

			if (fBackgroundColor != null)
				fBackgroundColor.dispose();

			fBackgroundColor= color;

			// ----------- selection foreground color --------------------
			color= fPreferenceStore.getBoolean(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SELECTION_FOREGROUND_DEFAULT_COLOR)
				? null
				: createColor(fPreferenceStore, AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SELECTION_FOREGROUND_COLOR, styledText.getDisplay());
			styledText.setSelectionForeground(color);

			if (fSelectionForegroundColor != null)
				fSelectionForegroundColor.dispose();

			fSelectionForegroundColor= color;

			// ---------- selection background color ----------------------
			color= fPreferenceStore.getBoolean(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SELECTION_BACKGROUND_DEFAULT_COLOR)
				? null
				: createColor(fPreferenceStore, AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SELECTION_BACKGROUND_COLOR, styledText.getDisplay());
			styledText.setSelectionBackground(color);

			if (fSelectionBackgroundColor != null)
				fSelectionBackgroundColor.dispose();

			fSelectionBackgroundColor= color;
			
            CCWPlugin.registerEditorColors(fPreferenceStore, styledText.getForeground().getRGB());
		}
    }
    
    /**
     * Creates a color from the information stored in the given preference store.
     * Returns <code>null</code> if there is no such information available.
     *
     * @param store the store to read from
     * @param key the key used for the lookup in the preference store
     * @param display the display used create the color
     * @return the created color according to the specification in the preference store
     */
    static public Color createColor(IPreferenceStore store, String key, Display display) {
        RGB rgb = null;

        if (store.contains(key)) {

            if (store.isDefault(key))
                rgb = PreferenceConverter.getDefaultColor(store, key);
            else
                rgb = PreferenceConverter.getColor(store, key);

            if (rgb != null)
                return new Color(display, rgb);
        }

        return null;
    }
}
