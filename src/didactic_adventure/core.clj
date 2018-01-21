(ns didactic-adventure.core
  (:require
    [clojure.data.json :as json]
    [clojure.tools.logging :as log]
    [org.httpkit.server :as web]
    [org.httpkit.client :as http]
    [didactic-adventure.brain :as brain])
  (:gen-class))

(defn port []
  (-> "PORT" System/getenv read-string))

(defn token []
  (->> "TOKEN" System/getenv (str "bot")))

(defn webhook-token []
  (System/getenv "WEBHOOK_TOKEN"))

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
  (let [chat-id  (-> message :chat :id)
        text     (-> message :text)
        reaction (brain/react text)]
    (if-not
      (nil? reaction) (do
                        (log/info "reply-to: " message)
                        (send-message chat-id reaction)))))

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
  (let [raw-body (slurp (:body request))
        webhook (json/read-str raw-body :key-fn keyword)
        message (:message webhook)]
    (do
      (log/info "webhook-arrived: " webhook)
      (reply-to message)
      {:status 204})))

(defn forbidden []
  {:status 403})

(defn app [request]
  (let [uri (:uri request)]
    (do
      (log/info uri " requested")
      (if (= uri (str "/" (webhook-token)))
        (webhook-arrived request)
        (forbidden)))))

(defn -main []
  (do
    (log/info "Starting server. Listen port " (port) "...")
    ;(hello)
    (web/run-server app {:port (port)})))
