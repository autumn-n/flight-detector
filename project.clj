(defproject flight-detector "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.json "0.2.6"]
                 [clj-http "3.9.1"]
                 [camel-snake-kebab "0.4.0"]
                 [medley "1.1.0"]]
  :main ^:skip-aot flight-detector.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
