(ns minesweeper.core
  (:require [clojure.string :as str]))

(defn- parse-cells
  [cells]
  (cond
    (str/blank? cells) []
    :else (conj (parse-cells (subs cells 1)) (subs cells 0 1))))

(defn- parse-rows
  [rows]
  (cond
    (empty? rows) []
    :else (conj
            (parse-rows
              (rest rows))
            (parse-cells
              (str/reverse (first rows))))))

(defn- proximity-of
  [[y x] grid [offset-y offset-x]]
  (cond
    (= "*" (get-in grid [(+ y offset-y) (+ x offset-x)])) 1
    :else 0))

(defn parse-field
  [field]
  (parse-rows (reverse (str/split-lines field))))

(defn determine-mines-proximity-of
  [coordinates grid]
  (let [deltas [[0 1]
                [0 -1]
                [-1 0]
                [1 0]
                [1 1]
                [-1 1]
                [-1 -1]
                [1 -1]]
        proximity-of' (partial proximity-of coordinates grid)]
    (reduce #(+ %1 (proximity-of' %2)) 0 deltas)))

(defn- is-mine
  [cell]
  (= "*" cell))

(defn- resolve-points
  [grid]
  (map-indexed
    (fn
      [y row]
      (map-indexed
        (fn
          [x cell]
          (if-not (is-mine cell)
            (determine-mines-proximity-of [y x] grid)
            cell))
        row))
    grid))

(def
  solve
  (let [join-lines (partial str/join "\n")
        join-cells (partial map #(str/join %1))]
    (comp
      join-lines
      join-cells
      resolve-points
      parse-field)))