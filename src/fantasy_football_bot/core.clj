(ns fantasy-football-bot.core
  (:require [fantasy-football-bot.sleeper :as sleeper]
            [fantasy-football-bot.json :as json]
            [vars]))


(sleeper/get-rosters vars/my-league)

