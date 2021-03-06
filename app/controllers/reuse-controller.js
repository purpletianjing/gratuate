'use strict';
var apiRequest = require('../services/api-request');
var constant = require('../mixin/constant');


function ReuseController() {

}

ReuseController.prototype.loadAccount = (req, res) => {
  if (req.session.user) {
    var userId = req.session.user.id;
    var url = 'users/' + userId;

    apiRequest.get(url, function (err, resp) {
      if (resp === undefined) {
        res.send({
          status: constant.httpCode.INTERNAL_SERVER_ERROR
        });
      } else if (resp.status === constant.httpCode.OK) {
        res.send({
          status: constant.httpCode.OK,
          account: resp.body.email
        });
      } else if (resp.status === constant.httpCode.NOT_FOUND) {
        res.send({
          status: constant.httpCode.NOT_FOUND
        });
      } else {
        res.status(constant.httpCode.INTERNAL_SERVER_ERROR);
        res.send({
          status: constant.httpCode.INTERNAL_SERVER_ERROR
        });
      }
    });
  }else {
    res.send({status: constant.httpCode.ACCEPTED});
  }
};

module.exports = ReuseController;
