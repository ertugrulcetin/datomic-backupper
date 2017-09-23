(ns datomic-backupper.core
  (:require [clojure.java.shell :as shell]
            [ring.adapter.jetty :as jetty]
            [datomic-backupper.logging :as log]
            [clojure.string :as str])
  (:gen-class)
  (:import (java.util.concurrent TimeUnit Executors)
           (java.util Date)
           (java.text SimpleDateFormat)))

;;Your instance needs at least 2GB Memory for this app!

(def conf {:db-uri         "datomic:ddb://us-east-1/your-table/your-db"
           :file-or-s3-uri "s3://your-bucket" ;it can be file:///Users/my-username/my-file-path
           :schedule       {:delay  0
                            :period 15
                            :unit   TimeUnit/MINUTES}
           :local?         false})

(def error-occurred? (atom false))


(defn back-up-to-s3-or-file
  []
  (let [d    (Date.)
        date (.format (SimpleDateFormat. "yyyy/MM/dd") d)
        time (.format (SimpleDateFormat. "HH-mm-ss") d)]
    (try
      (let [result (shell/sh (if (:local? conf)
                               "deployment/datomic/bin/datomic"
                               "my-datomic/bin/datomic") "backup-db" (:db-uri conf) (str (:file-or-s3-uri conf) "/" date "/" time))]
        (if-not (and (= 0 (:exit result)) (str/includes? (str result) ":succeeded"))
          (do
            (reset! error-occurred? true)
            (log/error (str "Datomic back up error! - Result: " result)))
          (log/info "Back up successfully completed.")))
      (catch Exception e
        (reset! error-occurred? true)
        (log/error "Datomic back up error!" e)))))


(defn handler
  [request]
  {:status  200
   :headers {"Content-Type" "text/html"}
   ;; /health endpoint can be used with ping services it's like health check endpoint
   :body    (if (= (:uri request) "/health")
              (try
                (if @error-occurred?
                  (throw (RuntimeException. "Error!"))
                  "OK")
                (catch Exception e
                  (reset! error-occurred? false)
                  (throw e)))
              "Hello")})


(defn back-up!
  []
  (.scheduleAtFixedRate (Executors/newScheduledThreadPool 1)
                        #(back-up-to-s3-or-file)
                        (-> conf :schedule :delay)
                        (-> conf :schedule :period)
                        (-> conf :schedule :unit)))


(defn start
  [port]
  (jetty/run-jetty handler {:port port}))


(defn -main
  []
  (back-up!)
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (start port)))