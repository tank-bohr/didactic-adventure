(ns didactic-adventure.slack
  (:require [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [gniazdo.core :as ws]
            [didactic-adventure.slack-db :as db]
            [didactic-adventure.http :as http]
            [didactic-adventure.brain :as brain]))

(defonce slack (atom nil))

(defonce message-counter (atom 0))

(defn data [token]
  (->
    "https://slack.com/api/rtm.start"
    (http/post {:token token})))

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
  (log/info "Received message:" message)
  (let [channel (:channel message)
        text (get message :text "")
        reaction (brain/react text)]
    (if-not (nil? text)
      (send-to channel reaction))))

(defn on-hello [_]
  (log/info "Hello"))

(defn on-member-joined-channel [message]
  (let [user    (-> message :user    db/find-user    :name)
        channel (-> message :channel db/find-channel :name)]
    (log/info user "joined the channel" channel)))

(defn on-member-left-channel [message]
  (let [user    (-> message :user    db/find-user    :name)
        channel (-> message :channel db/find-channel :name)]
    (log/info user "left the channel" channel)))

(defn on-channel-joined [message]
  (let [channel (-> message :channel :name)]
    (log/info "join the channel" channel)))

(defn on-channel-left [message]
  (let [channel (-> message :channel db/find-channel :name)]
    (log/info "left channel" channel)))

(defn on-team-join [message]
  (let [user (:user message)]
    (log/info "new user" user)
    (db/insert-user user)))

(defn on-channel-created [message]
  (let [channel (:channel message)]
    (log/info "new channel" channel)
    (db/insert-channel channel)))

(defn on-event [raw-message]
  (let [message (json/read-str raw-message :key-fn keyword)
        type (:type message)]
    (cond
      (= type "message") (on-message message)
      (= type "hello") (on-hello message)
      (= type "member_joined_channel") (on-member-joined-channel message)
      (= type "member_left_channel") (on-member-left-channel message)
      (= type "channel_joined") (on-channel-joined message)
      (= type "channel_left") (on-channel-left message)
      (= type "team_join") (on-team-join message)
      (= type "channel_created") (on-channel-created message))))

(defn connect [token]
  (let [data (data token)
        url (:url data)]
    (db/init)
    (db/load-data data)
    (if-not (nil? url)
      (reset! slack (ws/connect url :on-receive on-event)))))
