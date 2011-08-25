(ns clusearch.main 
    (:gen-class)
    (:require 
     [clusearch.build_index]
     [clusearch.query]
     [clusearch.query_server]
     )) 


(def commands 
     { 
     "query" { 
     , :desc "perform a query"
     , :run (fn [args] (clusearch.query/main args)) 
     }

     "query-server" { 
     , :desc "run a sever to perform queries through http"
     , :run (fn [args] (clusearch.query_server/main args)) 
     }

     "build-index" { 
     , :desc "query a index"
     , :run (fn [args] (clusearch.build_index/main args)) 
     }

     })


(defn help [] 
      (println "about commands:")       
      ;; TODO output the command name(key) and desc in a nice way
      (doseq [command  commands]
        (println command)))
 
(defn -main [& args]

  (let [ [cmd & more] args] 

    (if (commands cmd)
      (do
        println (commands cmd)
        println ( ((commands cmd) :run) (flatten  more) ) 
        )
      (help)
      ))) 
