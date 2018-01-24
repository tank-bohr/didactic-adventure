(ns didactic-adventure.core
  (:require
    [clojure.data.json :as json]
    [clojure.tools.logging :as log]
    [org.httpkit.server :as web]
    [didactic-adventure.slack :as slack]
    [didactic-adventure.http :as http]
    [didactic-adventure.brain :as brain])
  (:gen-class))

(defn port []
  (-> "PORT" System/getenv read-string))

(defn token []
  (->> "TOKEN" System/getenv (str "bot")))

(defn webhook-token []
  (System/getenv "WEBHOOK_TOKEN"))

(defn slack-token []
  (System/getenv "SLACK_TOKEN"))

(defn send-message-url []
  (str "https://api.telegram.org/" (token) "/sendMessage"))

(defn send-message [chat-id text]
  (->
    (send-message-url)
    (http/post {:chat_id chat-id
                :text text})
    :result))

(defn reply-to [message]
  (let [chat-id  (-> message :chat :id)
        text     (-> message :text)
        reaction (brain/react text)]
    (if-not
      (nil? reaction) (do
                        (log/info "reply-to: " message)
                        (send-message chat-id reaction)))))

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
  (let [port (port)
        slack-token (slack-token)]
    (log/info "Starting server. Listen port " port "...")
    (if-not (nil? slack-token)
      (slack/connect slack-token))
    (web/run-server app {:port port})))
