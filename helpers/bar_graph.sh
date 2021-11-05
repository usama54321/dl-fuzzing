set boxwidth 0.5
set style data histograms
set style histogram rowstacked
set style fill solid 1.0 border -1

set datafile separator " "
set xrange [-1:4]
set yrange [0:15]
set title "Branches Covered vs Inference Phase"

set xlabel "Phase"
set ylabel "Number of Branches"
plot filename using 2 t "Not Covered", '' using 3 t "Half Covered", '' using 4 t "Full Covered"
