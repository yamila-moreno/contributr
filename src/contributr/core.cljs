(ns ^:figwheel-always contributr.core
    (:require [cljs.reader :as reader]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

(defn compare-contributions [c1 c2]
  (compare (get c1 :date) (get c2 :date))
)

(defn contribution->html [c]
  (str "<p>" ( :user c) " - " ( :contribution c) " - " ( :url c) " - " ( :date c) "</p>")
)

(def req (js/XMLHttpRequest.))
(.open req "GET" "contributions.edn" false)
(.send req)
(def tmp (.-responseText req))
(def contributions (reader/read-string tmp))
(println (sort compare-contributions contributions))

(def html-contributions (js/document.querySelector ".contributions"))
(println html-contributions)


(set! (.-innerHTML html-contributions) (apply str (map contribution->html contributions)))




