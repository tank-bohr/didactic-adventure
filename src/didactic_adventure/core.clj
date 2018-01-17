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

(def get-updates-url
  (str "https://api.telegram.org/" token "/getUpdates"))

(def send-message-url
  (str "https://api.telegram.org/" token "/sendMessage"))

(defn get-updates []
  (->
    get-updates-url
    http/post
    deref
    :body
    (json/read-str :key-fn keyword)
    :result))

(defn send-message [chat-id text]
  (->
    send-message-url
    (http/post {:form-params
                {:chat_id chat-id
                 :text text}})
    deref
    :body
    (json/read-str :key-fn keyword)
    :result))

(defn reply-to [message]
  (let [chat-id (-> message :chat :id)
        text    (-> message :text)]
    (send-message chat-id text)))

(defn hello []
  (let [grouped (group-by #(-> % :message :chat :id) (get-updates))
        messages (map (fn [[_chat-id updates]]
                        (->> updates
                          (sort-by :update_id)
                          last
                          :message))
                   grouped)]
  (map reply-to messages)))

(defn app [_request]
  {:status 200
   :body "Welcome"})

(defn -main []
  (web/run-server app {:port port}))
