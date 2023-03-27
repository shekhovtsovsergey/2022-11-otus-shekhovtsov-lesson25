(function () {
    'use strict';

    function WelcomeController() {
    }

    angular
        .module('market.welcome')
        .component('appWelcome', {
            templateUrl: 'app/features/welcome/components/welcome/welcome.component.html',
            controller: WelcomeController,
        });
})();