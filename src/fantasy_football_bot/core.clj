(ns fantasy-football-bot.core
  (:require #_[clojure.core.cache :as cache]
            [fantasy-football-bot.sleeper :as sleeper]
            [fantasy-football-bot.json :as json]
            [vars]))


(sleeper/get-rosters vars/my-league)

(defn run
  []
  #_(let [all-players (sleeper/get-all-nfl-players)])
  (->
   (sleeper/get-rosters vars/my-league)
   (sleeper/rosters-to-starters-map)
   #_(sleeper/starters-map-to-injured-starters all-players)
   ))


(defn __ ;; Just using this in the REPL rn
  [all-players]
  (->>
   (run)    ;; Get map of "owner_id" to ["starter_id_1", "starter_id_2", etc]
   (first)
   (second) ;; first element is "owner_id", we want the vec of starter ID's
   (filter (fn [starter-id]
             [(sleeper/injured? ((keyword starter-id) all-players))]))))