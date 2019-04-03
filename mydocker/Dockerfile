#informacja na podstawie jakiego obrazu budujemy nasz obraz
FROM node:10

#instalacja narzędzia nodemon (nie trzeba robić restartu serwera po wprowadzeniu zmian w app.js)
RUN npm install -g nodemon

#tworzymy katalog node_modules i nadajemy mu prawa dostępu
RUN mkdir -p /home/node/app/node_modules && chown -R node:node /home/node/app

#ustawiamy bierzący katalog roboczy
WORKDIR /home/node/app

#kopiujemy do niego plik package*json
COPY package*.json ./

#instalujemy zależności naszego projektu
RUN npm install

#kopiujemy wszystkie pozosotałe pliki
COPY . .

#dbamy o ustawienie praw dostępu do poszczególny katalogów
COPY --chown=node:node . .

#zmieniamy użytkownika
USER node

#określamy na jakim porcie działa nasza aplikacja
EXPOSE 8080

# za pomocą node uruchamiamy serwer
# CMD["node", "app.js"]

#uruchamiamy polecenie nodemon
CMD [ "nodemon" ]