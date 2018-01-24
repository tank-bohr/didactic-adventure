(ns didactic-adventure.core
  (:require
    [clojure.data.json :as json]
    [clojure.tools.logging :as log]
    [org.httpkit.server :as web]
    [gniazdo.core :as ws]
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

(defn rtm-slack-url []
  (->
    "https://slack.com/api/rtm.start"
    (http/post {:token (slack-token)})
    :url))

(defonce slack (atom nil))

(defonce slack-message-counter (atom 0))

(defn send-to-slack [channel text]
  (if-not (nil? @slack)
    (->> {:id (reset! slack-message-counter (inc @slack-message-counter))
         :type "message"
         :channel channel
         :text text
       }
      json/write-str
      (ws/send-msg @slack))))

(defn on-slack-message [message]
  (let [channel (:channel message)
        text (:text message)
        reaction (brain/react text)]
    (send-to-slack channel reaction)))

(defn on-slack-event [raw-message]
  (let [message (json/read-str raw-message :key-fn keyword)
        type (:type message)]
    (if (= type "message") (do
                                (log/info "Received: " message)
                                (on-slack-message message)
                                ))))

(defn connect-slack []
  (if-not (nil? (slack-token))
    (let [url (rtm-slack-url)]
      (if-not (nil? url)
        (reset! slack (ws/connect url :on-receive on-slack-event))))))

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
  (do
    (log/info "Starting server. Listen port " (port) "...")
    (connect-slack)
    (web/run-server app {:port (port)})))
