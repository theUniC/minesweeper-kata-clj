(ns minesweeper.cli
  (:require [clojure.string :as str])
  (:require [minesweeper.core :refer [solve]])
  (:gen-class))

(defn- partial-right
  "Takes a function f and fewer than the normal arguments to f, and
 returns a fn that takes a variable number of additional args. When
 called, the returned function calls f with additional args + args."
  ([f] f)
  ([f arg1]
   (fn [& args] (apply f (concat args [arg1]))))
  ([f arg1 arg2]
   (fn [& args] (apply f (concat args [arg1 arg2]))))
  ([f arg1 arg2 arg3]
   (fn [& args] (apply f (concat args [arg1 arg2 arg3]))))
  ([f arg1 arg2 arg3 & more]
   (fn [& args] (apply f (concat args (concat [arg1 arg2 arg3] more))))))

(def ^{:private true} resolve-kata
  (let [print-solution (fn [idx solution] (println (str "Field #" (+ idx 1) ":")) (println solution) (println))]
    (comp
      (partial map-indexed print-solution)
      (partial map solve)
      (partial filter #(not (str/blank? %1)))
      (partial-right str/split #"\n?\d \d\n?"))))

(defn -main
  [& args]
  (print (resolve-kata (slurp *in*))))