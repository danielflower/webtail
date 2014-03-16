(function () {
    'use strict';

    var definition = {
        restrict: 'E',
        replace: true,
        scope: {

        },
        template: '<div>\n    <div>\n        <label><input type="checkbox" ng-model="follow"> follow</label>\n    </div>\n    <pre class="consoleContainer"><div ng-repeat="line in lines">{{line}}</div></pre>\n</div>',
        controller: ['$scope', 'logTailingService', function ($scope, logTailingService) {
            $scope.lines = [];
            $scope.count = 0;
            $scope.follow = true;
            var messageReceived = function (msg) {
                $scope.lines.push(msg);
                $scope.count++;
                $scope.$apply();
            };
            logTailingService.startTailing('hehe', messageReceived);
        }],
        link: function link(scope, element, attrs) {
            var panel = element.find('.consoleContainer').get(0);
            scope.$watch('count', function () {
                if (scope.follow) {
                    panel.scrollTop = panel.scrollHeight;
                }
            });
        }
    };

    angular.module('webtailApp.tailing')
        .directive('wtConsole', function () {
            return definition;
        });
})();