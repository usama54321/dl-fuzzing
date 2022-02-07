echo "usage bash fuzz.sh \$engine \$method \$in \$out \$cacheDir \$testclass "
mkdir -p $4
#mvn jqf:repro -Dclass=edu.ucla.cs.AppTest -DincludeOnly=edu.ucla.cs,net.tzolov,org/nd4j/tensorflow/conversion/graphrunner/ -Dinput=$1 -Dmethod=testProgramWithModel -Dengine=$2  -Djanala.verbose=true -Dout=$3

#,org/nd4j/tensorflow/conversion/graphrunner/,org.bytedeco.tensorflowlite.Interpreter
if [[ $1 == "tf" ]]
then
    mvn jqf:fuzz -Djanala.verbose=true -Djqf.ei.MAX_INPUT_SIZE=99999999 -Dclass=$6 -Dmethod=$2 -Dengine=$1 -Dout=$4 -DincludeOnly=edu.ucla.cs,net.tzolov -Din=$3 -Djanala.instrumentationCacheDir=$5 -Dfile.encoding="UTF-8"
fi

if [[ $1 == "zest" ]]
then
    mvn jqf:fuzz -Djanala.verbose=true -Djqf.ei.MAX_INPUT_SIZE=99999999 -Dclass=$6 -Dmethod=$2 -Dengine="zest" -Dout=$4 -DincludeOnly=edu.ucla.cs,net.tzolov -Djanala.instrumentationCacheDir=$5 -Dtime="2h" &> "$4/log"
fi

#,org/nd4j/tensorflow/conversion/graphrunner/,org.bytedeco.tensorflowlite.Interpreter
if [[ $1 == "random" ]]
then
    mvn jqf:fuzz -Djanala.verbose=true -Djqf.ei.MAX_INPUT_SIZE=99999999 -Dclass=$6 -Dmethod=$2 -Dblind=1 -Dout=$4 -DincludeOnly=edu.ucla.cs,net.tzolov,org/nd4j/tensorflow/conversion/graphrunner/,org.bytedeco.tensorflowlite.Interpreter -DnoCov=1 -Djanala.instrumentationCacheDir=$5 -Dtime="2h" &> $4/log
fi
