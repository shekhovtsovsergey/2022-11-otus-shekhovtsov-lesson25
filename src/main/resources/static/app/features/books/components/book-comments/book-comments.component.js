(() => {
    'use strict';

    function BookCommentsController(ApiService, $routeParams, $location) {
        const $ctrl = this;

        $ctrl.carts = [];

        $ctrl.$onInit = () => {
            $ctrl.id = $routeParams.id;

            if (!$ctrl.id) {
                console.error('Book-ID not found');
                $location.path('/');
            }

            $ctrl.loadCart();
        };

        $ctrl.loadCart = () => {
            ApiService.loadComments($ctrl.id).then((carts) => {
                $ctrl.carts = carts;
            });
        };
    }

    BookCommentsController.$inject = ['ApiService', '$routeParams', '$location'];

    angular
        .module('market.books')
        .component('appBookComments', {
            templateUrl: 'app/features/books/components/book-comments/book-comments.component.html',
            controller: BookCommentsController,
        });
})();