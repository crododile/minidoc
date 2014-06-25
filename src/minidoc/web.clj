(ns minidoc.web
  (:require [compojure.core :refer [defroutes GET]]
						[ring.adapter.jetty :as ring]
						[compojure.route :as route]
						[compojure.handler :as handler]
						[minidoc.controllers.docs :as docs]
						[minidoc.views.layout :as layout]
						[minidoc.models.migration :as schema])
	(:gen-class))
	
(defroutes routes
	docs/routes
	(route/resources "/")
	(route/not-found (layout/four-oh-four)))

(def application (handler/site routes))

(defn start [port]
	(ring/run-jetty application {:port port :join? false}))
	
(defn -main []
	(schema/migrate)
	(let [port (Integer. (or (System/getenv "PORT") "8080"))]
	(start port)))