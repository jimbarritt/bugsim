echo "$1 Your experiment $2 ($3) is finished" |  sed 's/%/ /g' > complete.txt

#mail -s "$1 - Experiment $2 ($3) Is complete. " jim@planet-ix.com < complete.txt

#To xargs this to echo you need to escape the escape characters aswell! so \\\"
echo "mail -s \"$1 Your experiment $2 ($3) is complete.\" jim@planet-ix.com < complete.txt" |  sed 's/%/ /g' > temp.sh

chmod +x temp.sh

./temp.sh

rm ./temp.sh


rm complete.txt

