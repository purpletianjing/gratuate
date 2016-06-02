'use strict';

var Reflux = require('reflux');
var UserCenterStore = require('../../store/user-center/user-center-store');
var UserCenterActions = require('../../actions/user-center/user-center-actions');

var FeedbackResult = React.createClass({
  mixins: [Reflux.connect(UserCenterStore)],

  getInitialState: function () {
    return {
      logicPuzzle: ''
    };
  },

  componentDidMount: function () {
    UserCenterActions.loadResult();
  },

  render() {
    var classString = (this.state.currentState === 'result' ? '' : ' hide');
    return (
        <div className={'col-md-9 col-sm-9 col-xs-12' + classString}>
          <div className="content show-result">
            <div className="logicPuzzle">
              <table className="table table-hover">
                <caption>逻辑题</caption>
                <thead>
                <tr>
                  <th>花费时间</th>
                  <th>当前进度</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                  <td>{this.state.logicPuzzle.time ? this.state.logicPuzzle.time : '--'}</td>
                  <td>{this.state.logicPuzzle.isCompleted ? '已完成' : '未完成'}</td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
    );
  }
});

module.exports = FeedbackResult;
