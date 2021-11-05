#column 4 is true, 5 is false. NR refers to line number
NR>1 {
    no_covered += $($4 == 0 && $5 == 0) ? 1 : 0;
    half_covered += ($4 == 0 && $5 > 0) || ($4 > 0 && $5 == 0) ? 1 : 0;
    full_covered += $5 > 0 && $4 > 0 ? 1 : 0;
} END {
    print(not_covered, half_covered, full_covered, NR-1)
}
