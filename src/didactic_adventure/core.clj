(ns didactic-adventure.core
  (:require
    [clojure.data.json :as json]
    [org.httpkit.server :as web]
    [org.httpkit.client :as http])
  (:gen-class))

(def token
  (System/getenv "TOKEN"))

(def port
  (System/getenv "PORT" ))

(defn get-updates-url []
  (str "https://api.telegram.org/" token "/getUpdates"))

(defn send-message-url []
  (str "https://api.telegram.org/" token "/sendMessage"))

(defn get-updates []
  (->
    (get-updates-url)
    http/post
    deref
    :body
    (json/read-str :key-fn keyword)
    :result))

(defn send-message [chat-id text]
  (->
    (send-message-url)
    (http/post {:form-params
                {:chat_id chat-id
                 :text text}})
    deref
    :body
    (json/read-str :key-fn keyword)
    :result))

(defn handle-update [update]
  (let [chat-id (-> update :message :chat :id)
        text    (-> update :message :text)]
    (send-message chat-id text)))

(defn run []
  (map handle-update (get-updates)))

(defn app [_request]
  {:status 200
   :body "Welcome"})

(defn -main []
  (web/run-server app {:port port}))
