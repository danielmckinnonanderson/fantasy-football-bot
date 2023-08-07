(ns fantasy-football-bot.sleeper
  (:require [clojure.pprint :as pprint]
            [clj-http.client :as client]
            [fantasy-football-bot.json :as json]))

(def ^:const sleeper-base-url "https://api.sleeper.app/v1/")
(def sleeper-user-base-url (str sleeper-base-url "user/"))
(def sleeper-league-base-url (str sleeper-base-url "league/"))

(def sleeper-nfl-players-url (str sleeper-base-url "players/nfl"))

(defn sleeper-drafts-for-league-url [league-id] (str sleeper-league-base-url league-id "/drafts"))
(defn sleeper-rosters-url [league-id] (str sleeper-league-base-url league-id "/rosters"))

(defn sleeper-get-user
  "Request Sleeper user data for the supplied user name or user ID.
   Evaluates to the body of the HTTP response from Sleeper, as a map"
  [user-id]
  (->> user-id
       (str sleeper-user-base-url)
       client/get
       :body
       json/parse))

(defn sleeper-get-league
  "Request Sleeper league data for the supplied league ID.
   Evaluates to the body of the response from Sleeper, as a map"
  [league-id]
  (->> league-id
       (str sleeper-league-base-url)
       client/get
       :body
       json/parse))

(defn sleeper-get-rosters
  "Request Sleeper rosters for the supplied league ID.
   Evaluates to the body of the response from Sleeper, as a list of maps (one map per roster)" 
  [league-id]
  (->> league-id
       sleeper-rosters-url
       client/get
       :body
       json/parse))


(defn sleeper-get-all-nfl-players
  "USE SPARINGLY. Response size is huge, roughly 5MB.
   Request data for all unique NFL players according to Sleeper.
   Includes all players that Sleeper has ever tracked, including retired / inactive players from yesteryear.
   Evaluates to the body of the response from Sleeper, a huge map where keys are the unique player ID's."
  []
  (-> sleeper-nfl-players-url
      client/get
      :body
      json/parse))

(defn sleeper-get-all-drafts 
  "Request all drafts for the supplied league ID.
   Return a list of maps representing draft data. Most recent at the front of the list."
  [league-id]
  (-> league-id
      (sleeper-drafts-for-league-url)
      client/get
      :body
      json/parse))

(defn sleeper-get-sport-state
  "Request state of the given sports league (IRL league)
   Documented options for sport are 'nfl' 'nba' 'lcs'"
  [sport]
  (throw (Exception. "Not yet implemented")))

