(ns fantasy-football-bot.core
  (:require #_[clojure.core.cache :as cache]
            [clojure.pprint]
            [fantasy-football-bot.sleeper :as sleeper]
            [fantasy-football-bot.json :as json]
            [vars]))


(sleeper/get-rosters vars/my-league)

(defn run
  []
  (->
   (let [all-players (sleeper/get-all-nfl-players)]
     (clojure.pprint/pprint all-players))))


;; (defn __ ;; Just using this in the REPL rn
;;   [all-players]
;;   (->>
;;    (sleeper/get-rosters vars/my-league)    ;; Get map of "owner_id" to ["starter_id_1", "starter_id_2", etc]
;;    (sleeper/rosters-to-starters-map)
;;    #_(map (fn [keys] [(take )(second)]))
;;    (second) ;; first element is "owner_id", we want the vec of starter ID's
;;    (filter (fn [starter-id]
;;              [(sleeper/injury-status ((keyword starter-id) all-players))]))))

(defn process [rosters all-players]
  (->> rosters
   (sleeper/rosters-to-starters-map)
   (reduce (fn [result [k1 v1]]
             (->> v1
                  (map #(-> % keyword (:all-players %)))
                  (map #(get all-players %))
                  (map #(get % :injury_status))
                  (filter #(and (not= nil %) (not= "QUES" %))) ;; TODO what are possibilities? PUP / QUES / DOUB / more?
                  (first)
                  (let [status-val #(when (some? %) %)]
                    (when status-val (assoc result k1 status-val)))))
           {})
       (into {})))
