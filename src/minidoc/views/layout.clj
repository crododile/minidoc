(ns minidoc.views.layout
	(:require [hiccup.page :as h])
	(:require [hiccup.element :as el]))
	
(defn common [title & body]
	(h/html5
	 [:head]
	  [:meta {:charse "utf-8"}]
		[:meta {:http-equiv "X-UA-Compatible" :content
						 "IE=edge,chrome=1"}]
		[:meta {:name "viewport" :content 
			"width=device-width, initial-scale=1, maximum-scale=1"}]
		[:title title]
		(h/include-css "/stylesheets/base.css"
									 "/stylesheets/skeleton.css"
									 "/stylesheets/screen.css"
									 "/stylesheets/mine.css")
	 [:body
	  [:h1 "DOC REVIEW JR"]
		[:nav
		 (el/link-to {:id "search"} "/" "Search")
		 (el/link-to {:id "add"} "/new_doc" "Add Doc to DB")
		 (el/link-to {:id "index"} "/index" "View All Docs")
		 ]
		[:div {:id "content" :class "container"} body]]))
		
(defn four-oh-four []
	(common "Page Not Found"
		[:div {:id "four-oh-four"}
		 "The page you requested could not be found"]))