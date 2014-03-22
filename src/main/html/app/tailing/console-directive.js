(function () {
    'use strict';


    angular.module('webtailApp.tailing')
        .directive('wtConsole', ['$document', 'logTailingService', function ($document, logTailingService) {
            return {
                restrict: 'E',
                replace: true,
                scope: {
                    logName: '@',
                    maxLines: '@',
                    follow: '&'
                },
                template: '<div>\n    <div>\n        <label class="stream"><input type="checkbox" ng-model="stream"> stream logs</label>\n        <label class="follow"><input type="checkbox" ng-model="follow"> follow</label>\n    </div>\n    <pre class="consoleContainer"></pre>\n</div>',
                link: function link(scope, element, attrs) {
                    // Not using Angular data binding as it is 5 times slower
                    // with large log files.
                    var panel = element.find('.consoleContainer').get(0);
                    var numDisplayed = 0;
                    var logLimit = scope.maxLines || 500;
                    scope.follow = true;
                    var messageReceived = function (msg) {
                        var el = document.createElement('div');
                        var value = msg.value;
                        el.appendChild(new Text(msg.number + ' ' + value));
                        panel.appendChild(el);

                        if (value.indexOf('ERROR') != -1) {
                            el.className = 'error';
                        } else if (value.indexOf('WARN') != -1) {
                            el.className = 'warn';
                        } else if (value.indexOf('**') != -1) {
                            el.className = 'success';
                        }

                        if (scope.follow) {
                            panel.scrollTop = panel.scrollHeight;
                        }
                        numDisplayed++;
                        if (numDisplayed > logLimit) {
                            panel.removeChild(panel.firstChild);
                        }
                    };
                    scope.$watch('stream', function (shouldStream) {
                        var logName = scope.logName;
                        if (shouldStream) {
                            logTailingService.startTailing(logName, messageReceived);
                        } else {
                            logTailingService.stopTailing(logName, messageReceived);
                        }
                    });

                }
            };
        }]);
})();