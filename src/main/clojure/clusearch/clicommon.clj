(ns clusearch.clicommon
    (:require [clargon.core :as cli])
    (:import 
      [java.io File]))

(defn common_opts [args]
  (cli/clargon 
    args 
    (cli/optional ["--index-dir" "location of the index to be queried" 
                   :default (str (-> "user.dir" ( System/getProperty)) (File/separator) ".clusearch") ])
    (cli/optional ["--analyzer-proxy" "load a proxied analyzer from a file"]) 
    )
  )

