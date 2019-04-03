//Przkładowa aplikacja klienta zwracająca coś (tutaj wersje noda)

const express = require('express');
const app = express();

app.get('/', (req, res) => res.send( { version: process.version } ));
//app.get('/', (req, res) => res.send('Elo makrelo!'));

app.listen(8080, () => console.log('Server is up!'));