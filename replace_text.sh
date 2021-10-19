#!//bin/bash

#usage : replace_text "file" "old text" "new text"

sed -i .tmp "s/$2/$3/" "$1"
rm "$1".tmp
