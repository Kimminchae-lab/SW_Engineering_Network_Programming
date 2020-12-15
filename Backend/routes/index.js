var express = require("express");
var crypto = require("crypto");
var uuid = require("uuid");
var mysql = require("mysql");
var bodyParser = require("body-parser");

// Create Connection with MySQL
var connection = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",
  database: "wakeup",
});

connection.connect(function (err) {
  if (err) {
    console.error("error connecting: " + err.stack);
    return;
  }
  console.log("connected as id " + connection.threadId);
});

module.exports = connection;
