(ns nrepl.module.core
  (:require [clojure.tools.nrepl :as repl]))
(println "before start-server compilation")
(defn start-server
  ([] (start-server 0))
  ([port] 
    (let [[ssocket _] (repl/start-server port)]
      (println (format "server started on port %d" (.getLocalPort ssocket)))
      #_(if (options "--repl")
          (run-repl (.getLocalPort ssocket) (when (options "--color") colored-output))
          ; need to hold process open with a non-daemon thread -- this should end up being super-temporary
          (Thread/sleep Long/MAX_VALUE)))))

(println "before start-server")
(start-server)
(println "after start-server")

