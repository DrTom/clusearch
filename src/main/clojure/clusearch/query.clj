(ns clusearch.query
    (:gen-class) 
    (:import 
     [java.io File] 
     [org.apache.lucene.store NIOFSDirectory]
     ) 
    (:require 
     [clargon.core :as cli]
     [clojure.tools.logging :as logging ]
     [clojure.set :as set] 
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
          (cli/required ["--query" "the query"] )))  

      config (atom {}) ]


    (logging/info (str "input options: " opts))

    (swap! config assoc :dir (NIOFSDirectory. (File. (opts :index-dir))))

    (if (opts :analyzer-proxy)
      (swap! config assoc :analyzer (load-file (opts :analyzer-proxy))))


    (logging/info (str "create_index_reader config" @config))

    (def index (create_index_reader @config)) 

    ; TODO list-tokens

    (def query_results 
         (if (opts :query)
           ((index :query) (opts :query) 30 )
           []))

    (doseq [result  query_results ]
           (println result))

    ))


(defn -main [& args]
      (main (flatten args)) )
