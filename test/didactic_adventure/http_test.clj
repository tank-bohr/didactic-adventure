(ns didactic-adventure.http-test
  (:require [clojure.test :refer :all])
  (:require [org.httpkit.fake :refer :all])
  (:require [didactic-adventure.http :refer :all]))

(deftest post-test
  (let [fake-response "{\"foo\": {\"bar\": \"baz\", \"num\": 17}}"
        url "https://example.com"]
    (testing "http post and json parse response"
      (with-fake-http [{:url url :method :post}
                       {:status 200 :body fake-response}]
          (is (=
                {:foo {:bar "baz"
                       :num 17}}
                (post url))))
        )))
