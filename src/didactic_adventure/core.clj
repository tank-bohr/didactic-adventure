(ns didactic-adventure.core
  (:require
    [immutant.web :as web])
  (:gen-class))

(defn app [request]
  {:status 200
   :body "Welcome"})

(defn -main []
  (web/run app))
