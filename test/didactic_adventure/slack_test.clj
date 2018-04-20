(ns didactic-adventure.slack-test
  (:require [clojure.test :refer [use-fixtures deftest testing is]])
  (:require [org.httpkit.server :refer [run-server
                                        with-channel
                                        on-close
                                        on-receive]])
  (:require [org.httpkit.fake :as fake])
  (:require [didactic-adventure.slack :refer [slack connect-with-token]]))

(def port 4321)

(def url (str "ws://localhost:" port))

(defn with-fake-http [f]
  (let [fake-response (str "{\"url\": \"" url "\"}")]
    (fake/with-fake-http [{:url "https://slack.com/api/rtm.start",
                           :method :post}
                          {:status 200
                           :body fake-response}]
      (f))))

(defn handler [request]
  (with-channel request channel))

(defn server []
  (run-server handler {:port port}))

(use-fixtures :once
   (fn [f]
     (let [stop-server (server)]
       (with-fake-http f)
       (stop-server))))

(deftest connect-test
  (testing "websocket connection"
    (connect-with-token "some-fake-token")
    (is (not (nil? @slack)))))
