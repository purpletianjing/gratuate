'use strict';

var Reflux = require('reflux');
var UserCenterActions = require('../../actions/user-center/user-center-actions');
var request = require('superagent');
var page = require('page');
var constant = require('../../../../mixin/constant');
var errorHandler = require('../../../../tools/error-handler.jsx');


var UserDetailStore = Reflux.createStore({
  listenables: [UserCenterActions],

  onLoadUserDetail: function () {
    request.get('/user-detail')
      .set('Content-Type', 'application/json')
      .use(errorHandler)
      .end((err, res) => {
        if (err) {
          return;
        } else if (res.body.status === constant.httpCode.ACCEPTED) {
          this.trigger({isThirdParty: true});
        } else if (res.body.status === constant.httpCode.OK) {
          this.trigger(res.body.data);
        } else {
          return;
        }
      });
  },

  onLoadResult : function () {
    request.get('/user/feedback-result')
        .set('Content-Type', 'application/json')
        .use(errorHandler)
        .end((err, res) => {
          if(res.body.httpCode === constant.httpCode.NOT_FOUND){
            this.trigger({
              logicPuzzle: ''
            });
          }else{
            this.trigger({
              logicPuzzle: res.body.logicPuzzle
            });
          }
        });
  },

  onUpdateUserDetail: function (userData) {
    request.put('/user-detail/update')
      .set('Content-Type', 'application/json')
      .send({
        data: userData
      })
      .use(errorHandler)
      .end((err, req) => {
        if (req.body.status === constant.httpCode.OK) {
          page('dashboard.html');
        } else {
          console.log('update error');
        }
      });
  },

  onChangeState: function (state, currentState) {
    if (state !== currentState) {
      this.trigger({
        currentState: state

      });
    }
  },

  onChangeGender: function (name) {
    this.trigger({gender: name});
  },

  onValidateGender: function (genderError) {
    if (genderError === true) {
      this.trigger({genderError: false});
    }
  },

  onCheckGender: function (gender) {
    if (gender === '') {
      this.trigger({genderError: true});
    } else {
      this.trigger({genderError: false});
    }
  }
});
module.exports = UserDetailStore;
