(ns didactic-adventure.core
  (:require
    [clojure.data.json :as json]
    [org.httpkit.server :as web]
    [org.httpkit.client :as http])
  (:gen-class))

(defn port []
  (-> "PORT" System/getenv read-string))

(defn token []
  (->> "TOKEN" System/getenv (str "bot")))

(defn webhook-token []
  (System/getenv "WEBHOOK-TOKEN"))

(defn get-updates-url []
  (str "https://api.telegram.org/" (token) "/getUpdates"))

(defn send-message-url []
  (str "https://api.telegram.org/" (token) "/sendMessage"))

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

(defn webhook-arrived [request]
  (let [raw-body (:body request)
        webhook (json/read-str raw-body {:key-fn keyword})
        message (:message webhook)]
    (do
      (reply-to message)
      {:status 204})))

(defn forbidden []
  {:status 403})

(defn app [request]
  (if (= (:uri request) (str "/" (webhook-token)))
    (webhook-arrived request)
    (forbidden)))

(defn -main []
  (do
    ;(hello)
    (web/run-server app {:port (port)})))
