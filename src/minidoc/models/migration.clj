(ns minidoc.models.migration
  (:require [clojure.java.jdbc :as sql]
            [minidoc.models.doc :as doc]))

(defn migrated? []
  (-> (sql/query doc/spec
                 [(str "select count(*) from information_schema.tables "
                       "where table_name='docs'")])
      first :count pos?))

(defn migrate []
  (when (not (migrated?))
    (print "Creating database structure...") (flush)
    (sql/db-do-commands doc/spec
                        (sql/create-table-ddl
                         :docs
                         [:id :serial "PRIMARY KEY"]
                         [:body :varchar "NOT NULL"]
                         [:created_at :timestamp
                          "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]))
    (println " done")))