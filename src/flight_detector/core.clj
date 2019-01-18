(ns flight-detector.core
  (:gen-class)
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [camel-snake-kebab.core :refer :all]
            [clojure.java.shell :refer [sh]])
  (:import (java.util Date)))

;; for KoreanAir

(def ^:private http-url "Please fill in YOUR URL here.")
(def ^:private http-cookie "Please fill in YOUR COOKIE here.")
(def ^:private header {:headers {:Cookie http-cookie}})
(def ^:private proxy {:proxy-host "Please fill in YOUR PROXY here."
                      :proxy-port 0000})

(defn alert [msg]
  (sh "osascript" "-e" (str "display notification \"" msg "\" with title \"Alert!\"")))

(defn get []
  (try
    (:body (client/get http-url (merge proxy header)))
    (catch Exception e (alert "STOP!"))))

(defn ->json-outbound [raw-body]
  (:outbound (json/read-str raw-body :key-fn ->kebab-case-keyword)))

(defn filtering [json-outbound]
  (let [after "2019-02-05T12"
        seats 3]
    (println (new Date) json-outbound)
    (->> json-outbound
         (map #(select-keys % [:departure :remaining-seats-by-booking-class]))
         (filter #(not (empty? (:remaining-seats-by-booking-class %))))
         (filter #(pos? (compare (:departure %) after)))
         (filter #(>= (+ (get-in % [:remaining-seats-by-booking-class :economyy] 0)
                         (get-in % [:remaining-seats-by-booking-class :prestigec] 0)) seats)))))

(defn detect [result]
  (if-not (empty? result) (alert "HURRY UP!")))

(defn exec []
  (while true
    (do
      (-> get
          ->json-outbound
          filtering
          detect)
      (Thread/sleep 30000))))

#_(exec)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (exec))
