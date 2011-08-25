(defproject clusearch "1.0.0"
            :description "A personal index and search ultily based on lucene and written in cloure."
            :dependencies [ 
                            [clargon "1.0.0"]
                            [clj-json "0.4.0"]
                            [cluwrap "1.1.0"]
                            [compojure "0.6.4"]
                            [log4j "1.2.15" :exclusions [javax.mail/mail javax.jms/jms com.sun.jdmk/jmxtools com.sun.jmx/jmxri]] 
                            [org.clojure/clojure "1.2.1"]
                            [ring/ring-jetty-adapter "0.3.11"]
                            ]
            :dev-dependencies [ 
                                [log4j "1.2.15" :exclusions [javax.mail/mail javax.jms/jms com.sun.jdmk/jmxtools com.sun.jmx/jmxri]]
                                [swank-clojure "1.3.2"] 
                                [lein-ring "0.4.5"] 
                                ]
            :source-path  "src/main/clojure" 
            :test-path "src/test/clojure"
            :resources-path "src/main/resources"
            :dev-resources-path "src/dev/resources"
            :aot [clusearch.main] 
            :main clusearch.main
            :uberjar-exclusions [#"BCKEY.SF"]
            )
