(() => {
    'use strict';

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                template: '<app-welcome></app-welcome>',
            })
            .when('/store', {
                template: '<app-store></app-store>',
            })
            .when('/store/add', {
                template: '<app-book-create></app-book-create>',
            })
            .when('/store/:id/comments', {
                template: '<app-book-comments></app-book-comments>',
            })
            .when('/store/:id', {
                template: '<app-book-edit></app-book-edit>',
            })
            .otherwise({
                redirectTo: '/',
            });
    }

    config.$inject = ['$routeProvider'];

    function run() {
        // tbd...
    }

    angular
        .module('market', [
            // external packages
            'ngRoute',
            'ngStorage',
            // internal packages
            'market.welcome',
            'market.store',
            'market.books',
        ])
        .config(config)
        .run(run);

})();