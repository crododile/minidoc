(ns minidoc.views.docs
	(:require [minidoc.views.layout :as layout]
						[hiccup.core :refer [h]]
						[hiccup.form :as form]
						[hiccup.element :as el]))
						
(defn doc-form []
	[:div {:id "doc-form" :class "add-form"}
		[:form {:action "/file" :method "post" :enctype "multipart/form-data"}
		 [:input {:name "file" :type "file" :size "20"}]
		 [:input {:type "submit" :name "submit" :value "submit"}]]
	 (form/form-to [:post "/"]
								 (form/label "body" "Copy Paste Document to DB")
								 (form/text-area "body")
								 (form/submit-button "Create Document"))])
								 
(defn terms-form []
	[:div {:id "doc-form" :class "search-form"}
	 (form/form-to [:post "/terms"]
				 (form/radio-button "all?" "all" "all")"RETURN ALL<br>"
				 (form/radio-button "all?" "either" "either")
				 "Return docs containing EITHER search term<br>"
				 (form/radio-button "all?" "both" "both")
				 "Return docs containing BOTH search terms<br>"
						 (form/label "first" "first search term")
						 (form/text-area "first")
						 (form/label "second" "second search term")
						 (form/text-area "second")
						 (form/submit-button "Mark it up"))])
								 
(defn display-docs [docs]
	[:div {:class "docs sixteen columns alpha omega"}
	 (map
	  (fn [doc] [:div {:class "doc"} (h (:body doc))])
		docs)])
		
(defn display-doc [[doc]]
	[:div {:class "docs sixteen columns alpha omega"}
		[:div {:class "doc"} (.replaceAll (h (:body doc)) (h (:target doc))
				 (str "<span style='color:blue'>" (h (:target doc)) "</span>"))]
		[:div {:class "doc"} (h (:match doc)) " " (h(:target doc)) "'s"]])	
	
(defn parseAll 
	"use regexes to replace search terms with spans that have highlighting css"
	[docs terms]
		(map
		  (fn [doc] [:div {:class "doc"}
				(clojure.string/replace (h (:body doc)) 
				  (re-pattern (str  (terms :first) "|" (terms :second)))
					 {(terms :first) (str "<span style='background-color:yellow'>" (terms :first) "</span>")
					 (terms :second) (str "<span style='background-color:red'>" (terms :second) "</span>")})
					 ])
		docs))
		
(defn or_matches [docs terms]
  (def parsed (parseAll docs terms))
	(layout/common "OR matches"
	[:div (str (count parsed) " documents matched EITHER search term")
	parsed]))
	
(defn and_matches [docs terms]
	(def parsed (parseAll docs terms))
	(layout/common "AND matches"
	[:div (str (count parsed) " documents matched BOTH search terms")
	parsed]))
	
(defn scanned_index [docs terms]
		(layout/common "all documents"
			(parseAll docs terms)))
			
(defn search_form []
	(layout/common "Search"
		(terms-form)))
		
(defn index [docs]
	(layout/common "Doc Review Jr."
		(display-docs docs)))
			
(defn show [doc]
	(layout/common "one doc"
	   (display-doc doc)))
		 
(defn new_doc []
	(layout/common "new doc"
		(doc-form)))
								 