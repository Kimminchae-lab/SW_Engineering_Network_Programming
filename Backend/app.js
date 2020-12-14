const express = require("express");
const app = express();
const mysql = require("mysql");

app.use(express.json());

app.listen(3000, () => {
  console.log("Listening on port 3000");
});
