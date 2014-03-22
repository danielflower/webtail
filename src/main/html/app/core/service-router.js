(function () {
    'use strict';

    /**
     * Handles requests to the services and responses
     * @name ServiceRouter
     */
    var ServiceRouter = function ($q) {

        var idFor = function (service, instance) {
            return service + ' ' + instance;
        };

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
                    var message = JSON.parse(event.data);
                    subscribers[idFor(message.name, message.instanceName)].forEach(function (subby) {
                        subby(message.data);
                    });
                };
                ws.onclose = function (event) {
                    socketPromise = null;
                    console.log('closed', event);
                };
            }
            return socketPromise.promise;
        };

        var hasSubscribers = function (id) {
            return !!subscribers[id];
        };

        var getSubscribersForService = function (id) {
            var subbies = subscribers[id];
            if (!subbies) {
                subbies = [];
                subscribers[id] = subbies;
            }
            return subbies;
        };

        this.subscribe = function (serviceName, instanceName, callback) {
            var id = idFor(serviceName, instanceName);
            var alreadySubscribed = hasSubscribers(id);
            getSubscribersForService(id).push(callback);
            if (!alreadySubscribed) {
                getSocket().then(function (ws) {
                    var request = {
                        name: serviceName,
                        action: 'subscribe',
                        instanceName: instanceName
                    };
                    ws.send(JSON.stringify(request));
                });
            }
        };

        this.unsubscribe = function (serviceName, instanceName, callback) {
            var id = idFor(serviceName, instanceName);
            if (hasSubscribers(id)) {
                subscribers[id] = subscribers[id].filter(function (val) {
                    return val !== callback;
                });
                if (subscribers[id].length === 0) {
                    delete subscribers[serviceName];
                    getSocket().then(function (ws) {
                        var request = {
                            name: serviceName,
                            action: 'unsubscribe',
                            instanceName: instanceName
                        };
                        ws.send(JSON.stringify(request));
                    });
                }
            }
        };

    };

    angular.module('webtailApp.core', [])
        .service('serviceRouter', ['$q', ServiceRouter]);
})();