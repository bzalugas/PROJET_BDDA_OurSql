#!/bin/bash

#use : add_head_file "directory" "extension" "head"

dir=$1
ext=$2
head=$3

nb=1
file=NULL
end=0

while [ $end -eq 0 ];
do
    file=$(find "$dir" -name "*$ext" -print | head -$nb | tail +$nb);
    if [ "$file" != "" ]
    then
        cat "$file" > temp.tmp
        echo "$head" > "$file"
        cat temp.tmp >> "$file"
        (("nb = nb + 1"))
    else
        end=1
    fi
done
rm temp.tmp
