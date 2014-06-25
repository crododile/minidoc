(ns minidoc.controllers.docs
	(:require [compojure.core :refer [defroutes GET POST]]
						[clojure.string :as str]
						[ring.util.response :as ring]
						[ring.middleware.multipart-params :as mp]
						[minidoc.views.docs :as view]
						[minidoc.mytika :as tika]
						[minidoc.models.doc :as model]))
						
(defn index []
	(view/index (model/all)))
	
(defn new_doc []
	(view/new_doc))
	
(defn search_form []
	(view/search_form))
	
(defn show [target]
	(view/show (model/one target)))

(defn wash-terms 
"If both search terms are the same 
set one to blank to prevent duplicate key erro"
	[terms]
	(if (= (:first terms) (:second terms))
	{:first (:first terms) :second "" :all? (:all? terms)}
	terms))
	
(defn scanned_index [terms]
	(view/scanned_index (model/all) (wash-terms terms)))
	
(defn and_matching_documents [terms]
	(view/and_matches (model/sql_and_filter terms) (wash-terms terms)))
	
(defn or_matching_documents [terms]
  (view/or_matches (model/sql_filter terms) (wash-terms terms)))
	
	
(defn create
	[doc]
	(println doc)
	(model/create doc)
	(ring/redirect "/"))
	
(defroutes routes
	(GET "/" [] (search_form))
	(GET "/index" [] (index))
	(GET "/new_doc" [] (new_doc))

	(POST "/" [& doc] (create doc))
	
	(mp/wrap-multipart-params
		(POST "/file" {params :params} 
			(create (tika/parser (:tempfile (:file params))))))
	(POST "/terms" [& terms] 
	(println terms)
		(if (= (:all? terms) "all")
			  (scanned_index terms)
				(if (=(:all? terms) "either")
					(or_matching_documents terms)
					(and_matching_documents terms)))))
					