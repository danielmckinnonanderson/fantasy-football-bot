(ns fantasy-football-bot.sleeper
  (:require [clojure.pprint :as pprint]
            [clojure.string :as string]
            [clj-http.client :as client]
            [fantasy-football-bot.json :as json]))

(def ^:const base-url "https://api.sleeper.app/v1/")
(def user-base-url (str base-url "user/"))
(def league-base-url (str base-url "league/"))

(def nfl-players-url (str base-url "players/nfl"))

(defn drafts-for-league-url [league-id] (str league-base-url league-id "/drafts"))
(defn rosters-url [league-id] (str league-base-url league-id "/rosters"))

(defn get-user
  "Request Sleeper user data for the supplied user name or user ID.
   Evaluates to the body of the HTTP response from Sleeper, as a map"
  [user-id]
  (->> user-id
       (str user-base-url)
       client/get
       :body
       json/parse))

(defn get-league
  "Request Sleeper league data for the supplied league ID.
   Evaluates to the body of the response from Sleeper, as a map"
  [league-id]
  (->> league-id
       (str league-base-url)
       client/get
       :body
       json/parse))

(defn get-rosters
  "Request Sleeper rosters for the supplied league ID.
   Evaluates to the body of the response from Sleeper, as a list of maps (one map per roster)" 
  [league-id]
  (->> league-id
       rosters-url
       client/get
       :body
       json/parse))


(defn get-all-nfl-players
  "USE SPARINGLY. Response size is huge, roughly 5MB.
   Request data for all unique NFL players according to Sleeper.
   Includes all players that Sleeper has ever tracked, including retired / inactive players from yesteryear.
   Evaluates to the body of the response from Sleeper, as an unprocessed string"
  []
  (-> nfl-players-url
      client/get
      :body
      json/parse))

(defn get-all-drafts 
  "Request all drafts for the supplied league ID.
   Return a list of maps representing draft data. Most recent at the front of the list."
  [league-id]
  (-> league-id
      (drafts-for-league-url)
      client/get
      :body
      json/parse))

(defn starters [roster]
  (:starters roster))

(defn get-sport-state
  "Request state of the given sports league (IRL league)
   Documented options for sport are 'nfl' 'nba' 'lcs'"
  [sport]
  (throw (Exception. "Not yet implemented")))


(defn rosters-to-starters-map
  "Transform the list of maps (rosters) into a map of maps, where the key is the user-id and the value is their starters"
  [rosters]
  (into {} 
        (map (fn [roster] [(:owner_id roster) (:starters roster)])
             rosters)))

(defn injury-status [player all-players]
  (:injury_status (player all-players))
  #_(if (:injury_status (player all-players))
      #_(:injury_status (player all-players))
      #_nil))


(defn starters-map-to-injured-starters
  "Transform into a map of keys owner_id (string) and values list of {:player_id :injury_status :status} for each injured starter.
   Only puts :owner_id key into the result if they have >= 1 injured player"
  [all-starters all-players]
  (into {} 
        ;; One specific fantasy team's starters
        #_(map (fn [starters] [(not (nil? (injury-status)))]
                all-starters)))) 


(defn starting-inactive?
  "Predicate indicating whether a provided roster map is starting a player who is inactive, according to the evaluator func.
   Evaluates to a list of the players who are starting despite being inactive"
  [roster all-players evaluator]
  (evaluator [roster all-players]))

