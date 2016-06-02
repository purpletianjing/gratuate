'use strict';

var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var constant = require('../mixin/constant');
var userHomeworkAnswer = require('./user-homework-answer');

var userHomeworkQuizzesSchema = Schema({
  userId: Number,
  paperId: Number,
  quizzes: [{
    id: Number,
    startTime: Number,
    userAnswerRepo: String,
    uri: String,
    branch: String,
    homeworkSubmitPostHistory: [{type: Schema.Types.ObjectId, ref: 'UserHomeworkAnswer'}]
  }]
});

userHomeworkQuizzesSchema.statics.initUserHomeworkQuizzes = function (userId, quizzes, paperId, callback) {
  this.findOne({userId: userId}, (err, doc) => {
    if (doc) {
      callback(new Error('is exist'), null);
    } else {
      var _quizzes = [];

      quizzes.forEach((quiz) => {
        _quizzes.push({
          id: quiz.id,
          uri: quiz.definition_uri
        });
      });

      this.create({
        userId: userId,
        paperId: paperId,
        quizzes: _quizzes
      }, callback);
    }
  });
};

userHomeworkQuizzesSchema.statics.getQuizStatus = function(userId, callback) {
  this.findOne({userId: userId})
    .populate('quizzes.homeworkSubmitPostHistory')
    .exec((err, doc) => {
      if (err || !doc) {
        callback(err || 'NOT_FOUND');
      } else {
        var result = [];

        doc.quizzes.forEach((item, index) => {
          var historyLength = item.homeworkSubmitPostHistory.length;

          if (historyLength) {
            result.push({
              status: item.homeworkSubmitPostHistory[historyLength - 1].status
            });
          } else if (!index) {
            result.push({
              status: constant.homeworkQuizzesStatus.ACTIVE
            });
          } else if (doc.quizzes[index - 1].homeworkSubmitPostHistory.length) {
            var lastStatus = doc.quizzes[index - 1].homeworkSubmitPostHistory.pop().status;

            if (lastStatus === constant.homeworkQuizzesStatus.SUCCESS) {
              result.push({
                status: constant.homeworkQuizzesStatus.ACTIVE
              });
            } else {
              result.push({
                status: constant.homeworkQuizzesStatus.LOCKED
              });
            }
          } else {
            result.push({
              status: constant.homeworkQuizzesStatus.LOCKED
            });
          }
        });

        callback(null, result);
      }
    });
};

userHomeworkQuizzesSchema.statics.findProgressTasks = function (callback) {
  this.find({quizzes: {$elemMatch: {status: constant.homeworkQuizzesStatus.PROGRESS}}}, 'userId quizzes', (err, doc) => {
    if (err) {
      callback(err);
    } else {
      var result = [];

      doc.forEach((item) => {
        var userAnswerRepo;
        var quizId;
        item.quizzes.forEach((quiz) => {
          userAnswerRepo = quiz.status === constant.homeworkQuizzesStatus.PROGRESS ? quiz.userAnswerRepo : userAnswerRepo;
          quizId = quiz.status === constant.homeworkQuizzesStatus.PROGRESS ? quiz.id : quizId;
        });

        result.push({
          userId: item.userId,
          quizId: quizId,
          userAnswerRepo: userAnswerRepo
        });
      });

      callback(null, result);
    }
  });
};

userHomeworkQuizzesSchema.statics.checkDataForUpdate = function (userId, homeworkId, callback) {
  var result = {};
  this.findOne({
    userId: userId,
    quizzes: {$elemMatch: {id: homeworkId}}
  }, {
    userId: 1,
    paperId: 1,
    quizzes: {$elemMatch: {id: homeworkId}}
  }, (err, data) => {
    if(err || !data) {
      callback(true);
    } else if(data.quizzes[0].status === constant.homeworkQuizzesStatus.PROGRESS ) {
      callback(null, data);
    } else {
      callback(true);
    }
  });
};

userHomeworkQuizzesSchema.statics.updateQuizzesStatus = function (data, callback) {
  this.findOne({userId: data.userId}, (err, doc) => {
    if (err || !doc) {
      callback(true);
    } else {
      doc.quizzes.forEach((item, i) => {
        if (item.id === data.homeworkId){
          var status = data.resultStatus ? constant.homeworkQuizzesStatus.SUCCESS : constant.homeworkQuizzesStatus.ERROR;
          doc.quizzes[i].status = status;
          doc.quizzes[i].homeworkSubmitPostHistory[doc.quizzes[i].homeworkSubmitPostHistory.length - 1].status = status;
          doc.quizzes[i].homeworkSubmitPostHistory[doc.quizzes[i].homeworkSubmitPostHistory.length - 1].resultURL = data.resultURL;
          doc.quizzes[i].homeworkSubmitPostHistory[doc.quizzes[i].homeworkSubmitPostHistory.length - 1].homeworkDetail = data.homeworkDetail;
        }
      });
      doc.save(callback);
    }
  });
};

module.exports = mongoose.model('UserHomeworkQuizzes', userHomeworkQuizzesSchema);
