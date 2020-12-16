const express = require("express");
const app = express();

app.use(express.json());

var mysql = require("mysql");
const { connect } = require("./routes");
var connection = mysql.createConnection({
  host: "localhost",
  user: "me",
  password: "1111",
  database: "wakeup",
});

connect.connect();

mongoClient.connect(url, (err, db) => {
  if (err) {
    console.log(err.message);
  } else {
    const myDb = db.db("myDb");
    const collection = myDb.collection("myTable");

    app.post("/signup", (req, res) => {
      const newUser = {
        name: req.body.name,
        email: req.body.email,
        password: req.body.password,
      };

      const query = { email: newUser.email };

      collection.findOne(query, (err, result) => {
        if (result == null) {
          collection.insertOne(newUser, (err, result) => {
            res.status(200).send();
          });
        } else {
          res.status(400).send();
        }
      });
    });

    app.post("/login", (req, res) => {
      const query = {
        email: req.body.email,
        password: req.body.password,
      };

      collection.findOne(query, (err, result) => {
        if (result != null) {
          const objToSend = {
            name: result.name,
            email: result.email,
          };

          res.status(200).send(JSON.stringify(objToSend));
        } else {
          res.status(404).send();
        }
      });
    });
  }
});

app.listen(3000, () => {
  console.log("Listening on port 3000...");
});
