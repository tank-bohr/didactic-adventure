(ns didactic-adventure.telegram
  (:require
    [clojure.data.json :as json]
    [clojure.tools.logging :as log]
    [org.httpkit.server :as server]
    [didactic-adventure.http :as http]
    [didactic-adventure.brain :as brain]))

(def token (memoize (fn []
  (->> "TELEGRAM_TOKEN" System/getenv (str "bot")))))

(def webhook-url (memoize (fn []
  (->> "WEBHOOK_URL" System/getenv (str "/")))))

(def send-message-url (memoize (fn []
  (str "https://api.telegram.org/" (token) "/sendMessage"))))

(defn send-message [chat-id text]
  (->
    (send-message-url)
    (http/post {:chat_id chat-id
                :text text})
    :result))

(defn reply-to [message]
  (let [chat-id  (-> message :chat :id)
        text     (get message :text "")
        reaction (brain/react text)]
    (if-not
      (nil? reaction) (do
                        (log/info "reply-to: " message)
                        (send-message chat-id reaction)))))

(defn webhook-arrived [request]
  (let [raw-body (slurp (:body request))
        webhook (json/read-str raw-body :key-fn keyword)
        message (:message webhook)]
    (log/info "webhook-arrived: " webhook)
    (reply-to message)
    {:status 204}))

(defn forbidden []
  {:status 403})

(defn app [request]
  (let [uri (:uri request)]
    (log/info uri " requested")
    (if (= uri (webhook-url))
      (webhook-arrived request)
      (forbidden))))

(defn start [port]
  (log/info "Starting server. Listen port " port "...")
  (server/run-server app {:port port}))
