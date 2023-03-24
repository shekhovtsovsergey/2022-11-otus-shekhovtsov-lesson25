(function () {
    'use strict';

    function NavbarController() {
        //tbd...
    }

    angular
        .module('market')
        .component('appNavbar', {
            templateUrl: 'app/components/navbar/navbar.component.html',
            controller: NavbarController,
        });
})();
