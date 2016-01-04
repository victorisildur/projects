var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('my_detail', { title: '我的番茄' });
});

module.exports = router;
