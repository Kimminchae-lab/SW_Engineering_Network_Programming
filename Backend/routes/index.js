var express = require("express");
var mysql = require("mysql");
var bodyParser = require("body-parser");
var app = express();
var path = require("path");
var router = express.Router();

// Create Connection with MySQL
var connection = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "1111",
  database: "wakeup",
});

connection.connect();

router.get("/", function (req, res) {
  res.sendFile(path.join(__dirname, "../../public/join.html"));
});

router.post("/", function (req, res) {
  var email = req.body.email;
  var name = req.body.name;
  var pw = req.body.password;

  var query = connection.query(
    'insert into user (username, userpw, useremail) values ("' +
      email +
      '","' +
      name +
      '","' +
      pw +
      '")',
    function (err, rows) {
      if (err) {
        throw err;
      }
      console.log("Data inserted!");
    }
  );
});

module.exports = router;
