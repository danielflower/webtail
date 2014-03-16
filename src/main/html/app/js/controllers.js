'use strict';

/* Controllers */

angular.module('webtailApp.controllers', ['webtailApp.core', 'webtailApp.tailing']).
  controller('MyCtrl1', ['$scope', 'serviceRouter', function($scope, serviceRouter) {
        $scope.hehe = 'hello ' + serviceRouter;
  }])
  .controller('MyCtrl2', [function() {

  }]);