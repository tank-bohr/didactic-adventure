(ns didactic-adventure.slack
  (:require [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [gniazdo.core :as ws]
            [didactic-adventure.http :as http]
            [didactic-adventure.brain :as brain]))

(defonce slack (atom nil))

(defonce message-counter (atom 0))

(defn rtm-url [token]
  (->
    "https://slack.com/api/rtm.start"
    (http/post {:token token})
    :url))

(defn send-to [channel text]
  (if-not (nil? @slack)
    (->>
      {:id (reset! message-counter (inc @message-counter))
       :type "message"
       :channel channel
       :text text}
      json/write-str
      (ws/send-msg @slack))))

(defn on-message [message]
  (let [channel (:channel message)
        text (:text message)
        reaction (brain/react text)]
    (send-to channel reaction)))

(defn on-event [raw-message]
  (let [message (json/read-str raw-message :key-fn keyword)
        type (:type message)]
    (if (= type "message") (do
                             (log/info "Received: " message)
                             (on-message message)
                             ))))

(defn connect [token]
  (let [url (rtm-url token)]
    (if-not (nil? url)
      (reset! slack (ws/connect url :on-receive on-event)))))
