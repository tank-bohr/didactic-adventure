(ns didactic-adventure.http
  (:require [clojure.data.json :as json]
            [org.httpkit.client :as client]))

(defn post
  ([url] (post url {}))
  ([url form-params]
   (-> url
     (client/post {:form-params form-params})
     deref
     :body
     (json/read-str :key-fn keyword))))
