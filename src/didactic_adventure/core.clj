(ns didactic-adventure.core
  (:require
    [clojure.tools.logging :as log]
    [didactic-adventure.slack :as slack]
    [didactic-adventure.telegram :as telegram])
  (:gen-class))

(defn -main []
  (log/info "Starting...")
  (slack/connect)
  (telegram/start))
