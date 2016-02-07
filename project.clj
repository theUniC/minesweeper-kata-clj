(defproject minesweeper "0.1.0"
  :description "Minesweeper Kata"
  :url "https://github.com/theUniC/minesweeper-kata-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.8.0"]
                 [speclj "3.3.1"]]
  :plugins [[speclj "3.3.0"]]
  :test-paths ["spec"]
  :main ^:skip-aot minesweeper.cli
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[speclj "3.3.0"]]}})
