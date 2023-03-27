(() => {
    'use strict';

    function StoreController($rootScope, ApiService, $q) {
        const $ctrl = this;

        $ctrl.bookList = [];
        $ctrl.genres = [];
        $ctrl.authors = [];

        $ctrl.loadBooks = () => {
            ApiService.loadBooks().then((bookList) => {
                $ctrl.bookList = bookList;
            });
        };

        $ctrl.deleteBook = (id) => {
            ApiService.deleteBook(id).then(() => {
                $ctrl.loadBooks();
            });
        };

        $ctrl.$onInit = () => {
            $ctrl.loadBooks();

            const getGenresPromise = ApiService.getGenres();
            const getAuthorsPromise = ApiService.getAuthors();

            $q.all({
                genres: getGenresPromise,
                authors: getAuthorsPromise,
            }).then((result) => {
                $ctrl.genres = result.genres;
                $ctrl.authors = result.authors;
            });
        };
    }

    StoreController.$inject = ['$rootScope', 'ApiService', '$q'];

    angular
        .module('market.store')
        .component('appStore', {
            templateUrl: 'app/features/store/components/store/store.component.html',
            controller: StoreController,
        });
})();