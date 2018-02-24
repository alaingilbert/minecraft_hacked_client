./recompile.sh
./reobfuscate.sh
rm -fr ./dist
mkdir -p ./dist
cp ./jars/versions/1.12/1.12.jar ./reobf/minecraft/manticore-1.12.jar
cd reobf/minecraft/
jar uf ./manticore-1.12.jar *
zip -d ./manticore-1.12.jar "META-INF/*"
mv ./manticore-1.12.jar ../../dist/manticore-1.12.jar
