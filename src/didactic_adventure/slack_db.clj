(ns didactic-adventure.slack-db
  (require [clojure.java.jdbc :as jdbc]
           [clojure.tools.logging :as log]
           [clojure.string :as string]))

(def spec {
         :classname "org.h2.Driver"
         :subprotocol "h2:mem"
         :subname "slack_db;DB_CLOSE_DELAY=-1"
         :user "didactic-adventure"
         :password ""
         })

(defn init []
  (jdbc/db-do-commands spec
    [(jdbc/create-table-ddl :users
       [[:id        "varchar(256)"]
        [:name      "varchar(256)"]
        [:real_name "varchar(256)"]
        [:is_bot    "boolean"]])
     (jdbc/create-table-ddl :channels
       [[:id              "varchar(256)"]
        [:name            "varchar(256)"]
        [:name_normalized "varchar(256)"]
        [:is_general      "boolean"]
        [:is_private      "boolean"]])
     "CREATE UNIQUE INDEX users_id_idx ON users(id);"
     "CREATE UNIQUE INDEX channels_id_idx ON channels(id);"]))

(defn table-name [table]
  (-> table name string/upper-case))

(defn show-columns [table]
  (let [table-name (table-name table)
        query (str "SHOW COLUMNS FROM " table-name)]
    (jdbc/query spec [query])))

(def columns
  (memoize (fn [table]
    (let [columns-info (show-columns table)]
      (map #(-> % :field string/lower-case keyword)
        columns-info)))))

(defn insert-entity [table entity]
  (let [columns (columns table)
        filtered (select-keys entity columns)]
    (jdbc/insert! spec table filtered)))

(defn find-entity [table id]
  (let [table-name (table-name table)
        query (str "SELECT * FROM " table-name " WHERE id = ?")]
    (first (jdbc/query spec [query id]))))

(defn load-collection [table collection]
  (log/info "load-collection for" table)
  (doseq [item collection]
    (insert-entity table item)))

(defn load-data [data]
  (let [users (:users data)
        channels (:channels data)]
    (load-collection :users users)
    (load-collection :channels channels)))

(def insert-user
  (partial insert-entity :users))

(def insert-channel
  (partial insert-entity :channels))

(def find-user
  (partial find-entity :users))

(def find-channel
  (partial find-entity :channels))
