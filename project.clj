(defproject minidoc "0.1.0-SNAPSHOT"
		:url "http://github.com/crododile/mini-docreview"
		:min-lein-version "2.0.0"
		:license {:name "Eclipse Public License"
		       :url "http://www.eclipse.org/legal/epl-v10.html"}
		:dependencies [[org.clojure/clojure "1.5.1"]
				           [org.clojure/java.jdbc "0.3.2"]
				           [postgresql "9.1-901.jdbc4"]
									 [ring/ring-jetty-adapter "1.2.1"]
									 [compojure "1.1.6"]
									 [org.clojars.floriano.clj-tika "1.2.4"]
									 [hiccup "1.0.4"]]
		:main ^:skip-aot minidoc.web
		:uberjar-name "minidoc-standalone.jar"
		:profiles {:uberjar {:aot :all}})
