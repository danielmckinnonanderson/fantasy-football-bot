(defproject fantasy-football-bot "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure     "1.11.1"]
                 [ring/ring-core          "1.9.1" ]
                 [ring/ring-jetty-adapter "1.9.1" ]
                 [compojure               "1.6.2" ]
                 [jarohen/chime           "0.3.3" ]
                 [clj-http                "3.12.3"]
                 [cheshire                "5.11.0"]]
  :repl-options {:init-ns fantasy-football-bot.core})
