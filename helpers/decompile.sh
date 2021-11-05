COUNT=0
for f in $(find $1 -name '*.class'); do
    DIR=$(dirname "${f}")
    DECOMPILED="$DIR/$(basename "${f}").decompiled"
    javap -c -private $f > $DECOMPILED
    COUNT=$(($COUNT+1))
done

echo "Decompiled $COUNT files";
