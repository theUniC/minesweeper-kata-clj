(ns minesweeper.core-spec
  (:require [speclj.core :refer :all]
            [minesweeper.core :refer :all]))

(describe "Minesweeper"
  (it "creates a list of a list of any size with or without mines"
    (should= [["." "*" "." "."] ["." "." "." "*"] ["." "." "." "."] ["*" "." "." "."]] (parse-field ".*..\n...*\n....\n*...")))

  (it "is able to determine, given a field of one row per two cell without mines, that the first cell has no mines around"
    (should= 0 (determine-mines-proximity-of [0 0] (parse-field ".."))))

  (it "is able to determine, given a field of one row and two cells, that the first cell has one mine around"
    (should= 1 (determine-mines-proximity-of [0 0] (parse-field ".*"))))

  (it "is able to determine, given a field of two rows per two cells, that the second cell on the top row has one mine on the previous cell"
    (should= 1 (determine-mines-proximity-of [0 1] (parse-field "*.\n.."))))

  (it "is able to determine, given a field of two rows and two cells, that the first cell on the bottom row has one mine on the cell above"
    (should= 1 (determine-mines-proximity-of [1 0] (parse-field "*.\n.."))))

  (it "is able to determine, given a field of two rows and two cells, that the second cell on the top row has one mine on the cell below"
    (should= 1 (determine-mines-proximity-of [0 1] (parse-field "..\n.*"))))

  (it "is able to determine, given a field of two rows and two cells, that the second cell on the bottom row has one on the cell above"
    (should= 1 (determine-mines-proximity-of [1 1] (parse-field ".*\n.."))))

  (it "is able to determine, given a field of 4x4, that there is one cell that has one mine in the previous upper diagonal cell"
    (should= 1 (determine-mines-proximity-of [3 3] (parse-field "....\n....\n..*.\n...."))))

  (it "is able to determine, given a field of 4x4, that there is one cell that has one mine in the previous lower diagonal cell"
    (should= 1 (determine-mines-proximity-of [2 3] (parse-field "....\n....\n....\n..*."))))

  (it "is able to determine, given a field of 4x4, that that the cell at (1, 1) has a mines proximity of 2"
    (should= 2 (determine-mines-proximity-of [1 1] (parse-field "**..\n....\n....\n...."))))

  (it "is able to determine, given a field of 4x4, that that the cell at (1, 1) has a mines proximity of 3"
    (should= 3 (determine-mines-proximity-of [1 1] (parse-field "**..\n*...\n....\n...."))))

  (let [fields-and-solutions [["*...\n....\n.*..\n...." "*100\n2210\n1*10\n1110"]
                              ["**..\n*..*\n..*.\n..**" "**21\n*43*\n13*4\n02**"]
                              ["***.\n*..*\n..*.\n..**" "***2\n*54*\n13*4\n02**"]
                              ["*...\n....\n.*..\n...." "*100\n2210\n1*10\n1110"]
                              ["**...\n.....\n.*..."    "**100\n33200\n1*100"]]
        try-solve (fn [[field solution]] (should= solution (solve field)))]
    (it "is able to resolve any field"
      (map try-solve fields-and-solutions))))
