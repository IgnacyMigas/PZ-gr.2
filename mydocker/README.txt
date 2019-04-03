//Pobierz docker desktop, nastêpnie uruchom PowerShell
//sprawdŸ, czy masz docker'a
docker

//przejdz do katalogu projektu
cd Desktop/mydocker

//utwórz package.json
npm init --yes
//pobierz node_modules (experss)
npm install --save express

//utwórz app.js i pó¿niej uruchom
node app.js

//uruchom  http://localhost:8080/

#create dockerfile

//flaga -t to tagowanie obrazu
//kropka to zaznaczenie ze chodzi o biez¹cy katolg
//wykonanie kolejnych kroków z pliku dockerfile
docker build -t mydocker/demo .

//uruchomienie obrazu (wersja 1 gorsza)
// flaga p wskzuje jaki port na moim komputerze ma odpowiadaæ port który zdefiniowaliœmy w dockerfile 
//nastêpnie wskazuje o jaki obraz mi chodzi
docker run -p 8000:8080 mydocker/demo
//informacje, np identyfikator,¿eby zatrzymaæ kontener
docker ps
docker stop id

//uruchomienie obrazu (wersja 2 lepsza, bo nie trzeba dawaæ stop)
//gdy zmiany w kontynerze, a nie na kompie to musimy po³aczyæ zmiany
//po fladze v podajemy lokaln¹ œcie¿kê oraz wskazuje scie¿kê katalogu roboczego w kontenerze
//na koncu podajê nazwê obrazu
docker run -p 8000:8080 -v /Users/TomaszK¹kol/Desktop/mydocker/:/home/node/app mydocker/demo