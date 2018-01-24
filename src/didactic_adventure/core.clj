(ns didactic-adventure.core
  (:require
    [clojure.tools.logging :as log]
    [didactic-adventure.slack :as slack]
    [didactic-adventure.telegram :as telegram])
  (:gen-class))

(defn port []
  (-> "PORT" System/getenv read-string))

(defn slack-token []
  (System/getenv "SLACK_TOKEN"))

(defn -main []
  (let [port (port)
        slack-token (slack-token)]
    (log/info "Starting...")
    (if-not (nil? slack-token)
      (slack/connect slack-token))
    (telegram/start port)))
