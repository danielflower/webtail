(function () {
    'use strict';

    /**
     * Handles requests to the services and responses
     * @name ServiceRouter
     */
    var ServiceRouter = function ($q) {

        var socketPromise = null;


        var subscribers = {};
        var getSocket = function () {
            if (!socketPromise) {
                socketPromise = $q.defer()
                var ws = new WebSocket("ws://localhost:8080/echoit");
                ws.onopen = function (event) {
                    console.log('connected', event);
                    socketPromise.resolve(ws);
                };
                ws.onmessage = function (event) {
                    subscribers['startTailing'].forEach(function (subby) {
                        subby(event.data);
                    });
                };
                ws.onclose = function (event) {
                    //socket = null;
                    console.log('closed', event);
                };
            }
            return socketPromise.promise;
        };

        this.subscribe = function (serviceName, parameters, callback) {
            if (!subscribers[serviceName]) {
                subscribers[serviceName] = [];
            }
            subscribers[serviceName].push(callback);
            getSocket().then(function (ws) {
                var request = {
                    service: serviceName,
                    params: parameters
                };
                ws.send(JSON.stringify(request));
            });
        };

        this.unsubscribe = function (serviceName, callback) {
            subscribers[serviceName] = subscribers[serviceName].filter(function (val) {
                return val !== callback;
            });
        };

    };

    angular.module('webtailApp.core', [])
        .service('serviceRouter', ['$q', ServiceRouter]);
})();