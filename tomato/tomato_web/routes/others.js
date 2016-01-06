var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/showoff', function(req, res, next) {
  res.render('others_showoff', { title: '别人的番茄' });
});

module.exports = router;
