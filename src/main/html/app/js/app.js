'use strict';


// Declare app level module which depends on filters, and services
angular.module('webtailApp', [
        'ngRoute',
        'webtailApp.core',
        'webtailApp.tailing',
        'webtailApp.filters',
        'webtailApp.services',
        'webtailApp.directives',
        'webtailApp.controllers'
    ]).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/view1', {templateUrl: 'partials/partial1.html', controller: 'MyCtrl1'});
        $routeProvider.when('/view2', {templateUrl: 'partials/partial2.html', controller: 'MyCtrl2'});
        $routeProvider.otherwise({redirectTo: '/view1'});
    }]);

angular.module('webtailApp.tailing', ['webtailApp.core']);
