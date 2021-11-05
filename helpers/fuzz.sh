echo "usage bash repro.sh \$engine \$out"
#mvn jqf:repro -Dclass=edu.ucla.cs.AppTest -DincludeOnly=edu.ucla.cs,net.tzolov,org/nd4j/tensorflow/conversion/graphrunner/ -Dinput=$1 -Dmethod=testProgramWithModel -Dengine=$2  -Djanala.verbose=true -Dout=$3
mvn -e -X jqf:fuzz -Djanala.verbose=true -Djqf.ei.MAX_INPUT_SIZE=99999999 -Dclass=edu.ucla.cs.AppTest -Dmethod=testProgramWithModel  -Dengine=$1 -Dout=$2 -DincludeOnly=edu.ucla.cs,net.tzolov,org/nd4j/tensorflow/conversion/graphrunner/
