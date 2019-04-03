//Pobierz docker desktop, nast�pnie uruchom PowerShell
//sprawd�, czy masz docker'a
docker

//przejdz do katalogu projektu
cd Desktop/mydocker

//utw�rz package.json
npm init --yes
//pobierz node_modules (experss)
npm install --save express

//utw�rz app.js i p�niej uruchom
node app.js

//uruchom  http://localhost:8080/

#create dockerfile

//flaga -t to tagowanie obrazu
//kropka to zaznaczenie ze chodzi o biez�cy katolg
//wykonanie kolejnych krok�w z pliku dockerfile
docker build -t mydocker/demo .

//uruchomienie obrazu (wersja 1 gorsza)
// flaga p wskzuje jaki port na moim komputerze ma odpowiada� port kt�ry zdefiniowali�my w dockerfile 
//nast�pnie wskazuje o jaki obraz mi chodzi
docker run -p 8000:8080 mydocker/demo
//informacje, np identyfikator,�eby zatrzyma� kontener
docker ps
docker stop id

//uruchomienie obrazu (wersja 2 lepsza, bo nie trzeba dawa� stop)
//gdy zmiany w kontynerze, a nie na kompie to musimy po�aczy� zmiany
//po fladze v podajemy lokaln� �cie�k� oraz wskazuje scie�k� katalogu roboczego w kontenerze
//na koncu podaj� nazw� obrazu
docker run -p 8000:8080 -v /Users/TomaszK�kol/Desktop/mydocker/:/home/node/app mydocker/demo