(ns fantasy-football-bot.sleeper
  (:require [clojure.pprint :as pprint]
            [clj-http.client :as client]
            [fantasy-football-bot.json :as json]))

(def ^:const sleeper-base-url "https://api.sleeper.app/v1/")
(def sleeper-user-url (str sleeper-base-url "user/"))
(def sleeper-league-url (str sleeper-base-url "league/"))

(defn sleeper-get-user
  "Request Sleeper user data for the supplied user name or user ID.
   Evaluates to the body of the HTTP response from Sleeper, as a map"
  [user-id]
  (->> user-id
       (str sleeper-user-url)
       client/get
       :body
       json/parse))

(defn sleeper-get-league
  "Request Sleeper league data for the supplied league ID.
   Evaluates to the body of the response from Sleeper, as a map"
  [league-id]
  (->> league-id
       (str sleeper-league-url)
       client/get
       :body
       json/parse))
