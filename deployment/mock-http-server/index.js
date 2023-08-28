const express = require('express');
const PORT = 3000;
var {commerces} = require('./commerces');
const app = express();

app.use(express.json())

app.get("/commerces", (req, res) => {
    res.header('Content-type', 'application/json');
    res.send(commerces);
});

app.post("/commerces", (req, res) => {
    res.header('Content-type', 'application/json');
    const commercesId = commerces.map(commerce => commerce.id);
    let commerceSecuence = Math.max(...commercesId);
    const buttonsId = commerces.flatMap(commerce => commerce.buttons).map(button => button.id);
    let buttonSecuence = Math.max(...buttonsId);
    let data = req.body;
    data.id = ++commerceSecuence;
    for (let index = 0; index < data.buttons.length; index++) {
        data.buttons[index].id = ++buttonSecuence;
    }
    commerces.push(data);
    res.send(data);
})

app.listen(PORT, () => {
    console.log(`Listen on port: ${PORT}`)
});
