(() => {
    'use strict';

    function BookEditController(ApiService, $routeParams, $q, $location) {
        const $ctrl = this;
        $ctrl.book = undefined;
        $ctrl.bookForm = undefined;
        $ctrl.genres = [];
        $ctrl.authors = [];

        $ctrl.$onInit = () => {
            $ctrl.id = $routeParams.id;

            const getBookPromise = ApiService.getBook($ctrl.id);
            const getGenresPromise = ApiService.getGenres();
            const getAuthorsPromise = ApiService.getAuthors();

            $q.all({
                book: getBookPromise,
                genres: getGenresPromise,
                authors: getAuthorsPromise,
            }).then((result) => {
                $ctrl.book = result.book;
                $ctrl.genres = result.genres;
                $ctrl.authors = result.authors;
            });
        };

        $ctrl.submitForm = () => {
            if ($ctrl.bookForm.$invalid) {
                alert('INVALID FORM');
                return;
            }

            ApiService.updateBook($ctrl.book).then(() => {
                $location.path('/store');
            }).catch((error) => {
                console.error('error', error);

                alert('Error: ' + error.message || 'unknown error');
            });
        };
    }

    BookEditController.$inject = ['ApiService', '$routeParams', '$q', '$location'];

    angular
        .module('market.books')
        .component('appBookEdit', {
            templateUrl: 'app/features/books/components/book-edit/book-edit.component.html',
            controller: BookEditController,
        });
})();
