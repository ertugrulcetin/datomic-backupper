(defproject datomic-backupper "0.1.0-SNAPSHOT"

  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.6.0"]
                 [org.slf4j/slf4j-api "1.7.25"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.logback-extensions/logback-ext-loggly "0.1.4"]]

  :uberjar-name "datomic-backupper.jar"

  :main datomic-backupper.core)