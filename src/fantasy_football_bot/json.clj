(ns fantasy-football-bot.json
  (:require [cheshire.core :refer :all]))


(defn parse
  "Parse a JSON string into a map, with the JSON keys as :keywords"
  [s]
  (cheshire.core/parse-string s true))

(defn serialize
  "Serialize a JSON map into a string, :some-key becomes "
  [v]
  (cheshire.core/generate-string v { :pretty true }))
