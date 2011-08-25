(ns clusearch.query_server
    (:gen-class) 
    (:import 
     [java.io File] 
     [org.apache.lucene.store NIOFSDirectory]
     ) 
    (:require 
     [compojure.core :as cc]
     [compojure.route :as cr] 
     [compojure.handler :as ch]
     [ring.adapter.jetty :as rj]
     [clargon.core :as cli]
     [clojure.set :as set] 
     [clojure.tools.logging :as logging ]
     [clj-json.core :as json]
     )
    (:use 
     [cluwrap.filehelper] 
     [cluwrap.core]  
     [cluwrap.extract]
     [clusearch.clicommon]
     ))


(defn 
  main [args]
  (let 
    [ opts 
      (set/union
        (common_opts args)
        (cli/clargon 
          args 
          (cli/optional ["--port" :default "9876"])
          ))  

      config (atom {}) ]

    (logging/info (str "input options: " opts)) 

    (defn json-response [data & [status]]
      { :status (or status 200)
      , :headers {"Content-Type" "application/json"}
      , :body (json/generate-string data)})


    (swap! config assoc :dir (NIOFSDirectory. (File. (opts :index-dir))))

    (if (opts :analyzer-proxy)
      (swap! config assoc :analyzer (load-file (opts :analyzer-proxy))))


    (logging/info (str "create_index_reader config" @config))

    (def index (create_index_reader @config)) 

;    ; TODO list-tokens
 
    (defn search [query]  ((index :query) query 30 ))


    (cc/defroutes routes
                  (cc/GET "*" {params :params}  
                           (do
                            (logging/info (str  (or params "no parmeters"))) 
                            (json-response (search ( params :query)))
                            )) 
                  
                  (cr/not-found "Page not found")
                  )

    (def wraped_routes (ch/api routes)) 


    (rj/run-jetty wraped_routes {:port (Integer/parseInt ( opts :port))})

    ))
  

(defn -main [& args]
      (main (flatten args)) )
