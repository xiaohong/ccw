(ns ccw.activator
  (:require [clojure.osgi.core :as osgi]))

(def *aot-classes*
  [
   "ccw.editors.antlrbased.OpenDeclarationAction"
   "ccw.ClojureProjectNature"
   "ccw.editors.antlrbased.PareditAutoEditStrategy"
   "ccw.editors.antlrbased.ClojureFormat"
   "ccw.editors.antlrbased.StacktraceHyperlink"
   "ccw.editors.antlrbased.ExpandSelectionUpAction"
   "ccw.editors.antlrbased.ExpandSelectionLeftAction"
   "ccw.editors.antlrbased.ExpandSelectionRightAction"
   "ccw.editors.antlrbased.RaiseSelectionAction"
   "ccw.editors.antlrbased.IndentSelectionAction"
   "ccw.editors.antlrbased.SplitSexprAction"
   "ccw.editors.antlrbased.JoinSexprAction"
   "ccw.editors.antlrbased.SwitchStructuralEditionModeAction"
   "ccw.editors.antlrbased.EditorSupport"
   "ccw.editors.antlrbased.ClojureHyperlink"
   "ccw.editors.antlrbased.ClojureHyperlinkDetector"
   ])

(defn bundle-start [context]
  (println "ccw.core's bundle-start called")
  (doseq [c *aot-classes*] 
    (osgi/load-aot-class context c))
  (-> (ccw.CCWPlugin.) (.start context)))

(defn- bundle-stop [context]
  (-> (ccw.CCWPlugin/getDefault) (.stop context)))

