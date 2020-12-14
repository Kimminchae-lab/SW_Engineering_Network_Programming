var mysql = require("mysql");

var connection = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "12345678",
  database: wakeup,
});

connection.connect();

connection.query("SELECT * FROM user", function (error, result, fields) {
  if (error) {
    console.log(error);
  }
  console.log(result);
});

connection.end();
