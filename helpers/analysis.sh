cat ./tf-results/tf-log-* | sort | cut -d"," -f1,2,3 | uniq
#summation
#cat ./zest-results/tf-log-* | sort | awk -F ',' '{temp = sprintf("%s.%s%s", $1, $2, $3); a[temp] += $4; b[temp] += $5} END{for (key in a) { printf("%s %d %d\n", key, a[key], b[key])}}
