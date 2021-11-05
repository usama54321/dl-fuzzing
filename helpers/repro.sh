echo "usage bash repro.sh \$input \$engine \$out"
rm -r $3/instrumentation

mvn jqf:repro -Dclass=edu.ucla.cs.AppTest -DincludeOnly=edu.ucla.cs,net.tzolov,org/nd4j/tensorflow/conversion/graphrunner/ -Dinput=$1 -Dmethod=testProgramWithModel -Dengine=$2  -Djanala.verbose=true -Dout=$3 -Djanala.instrumentationCacheDir="$3/instrumentation"
