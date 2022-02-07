echo "usage \$app \$basefolder"

BASE=$2
declare -a types=("ORIG" "zest" "random")

if [[ $1 == "pose" ]]
then
    CLASS="edu.ucla.cs.PoseTest"
    METHOD="testPoseEstimation"
fi

if [[ $1 == "crop" ]]
then
    CLASS="edu.ucla.cs.CropTest"
    METHOD="testSmartCropper"
fi

if [[ $1 == "face" ]]
then
    CLASS="edu.ucla.cs.FaceTest"
    METHOD="testFaceDetection"
fi

for typ in "${types[@]}"
do
    FOLDER=$BASE/$typ
    if [[ -d $FOLDER ]]
    then
        OUT=$FOLDER/repro

        rm -r $OUT
        mkdir -p $OUT


        if [[ $typ == "zest" || $typ == "random" ]]
        then
            METH="${METHOD}Random"
        else
            METH="${METHOD}"
        fi

        bash helpers/repro.sh $FOLDER/corpus tf $OUT $METH $CLASS &> $OUT/log
    fi
done
