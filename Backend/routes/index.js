var express = require("express");
var mysql = require("mysql");
var parser = require("body-parser");
var app = express();
var router = express.Router();

var connection = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "1111",
  database: "wakeup",
});

//connection.connect();

app.use(parser.json());
app.use(parser.urlencoded({ extended: true }));

app.post("/register", (req, res, next) => {
  var name = req.body.name;
  var email = req.body.email;
  var pw = req.body.password;

  connection.query(
    "SELECT * FROM user WHERE email = ?",
    [email],
    function (err, result, fields) {
      connection.on("error", function (err) {
        console.log("[MySQL ERROR]", err);
      });
      var sql =
        "INSERT INTO user (username, userpw, useremail), VALUES (?, ?, ?)";
      var params = [name, pw, email];
      if (result && result.length) {
        res.json("User already exists!!!");
      } else {
        connection.query(sql, params, function (err, rows, fields) {
          connection.on("error", function (err) {
            console.log("[MySQL ERROR]", err);
            res.json("Register error: ", err);
          });
          res.json("Register successful");
        });
      }
    }
  );
});

app.post("/login/", (req, res, next) => {
  connection.query(
    "SELECT * FROM user WHERE email = ?",
    [email],
    function (err, result, fields) {
      connection.on("error", function (err) {
        console.log("[MySQL ERROR]", err);
      });
      var sql =
        "INSERT INTO user (username, userpw, useremail), VALUES (?, ?, ?)";
      var params = [name, pw, email];
      if (result && result.length) {
        if (result[0] == pw) {
          res.end(JSON.stringify(result[0]));
        } else {
          res.end(JSON.stringify("Wrong password"));
        }
      } else {
        res.json("Loged In");
      }
    }
  );
});
app.listen(3000, () => {
  console.log("Running");
});
module.exports = router;
