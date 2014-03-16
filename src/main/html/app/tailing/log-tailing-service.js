
(function () {
    'use strict';

    /**
     *
     * @param {ServiceRouter} serviceRouter
     * @constructor
     */
    var LogTailingService = function (serviceRouter) {

        /**
         * Starts a subsciption to the log with the given instance name.
         * @param {String} logName The name of the log
         * @param callback Callback containing each log line
         */
        this.startTailing = function (logName, callback) {
            serviceRouter.subscribe('startTailing', { logName: logName }, callback);
        };

    };

    angular.module('webtailApp.tailing')
        .service('logTailingService', ['serviceRouter', LogTailingService]);
})();