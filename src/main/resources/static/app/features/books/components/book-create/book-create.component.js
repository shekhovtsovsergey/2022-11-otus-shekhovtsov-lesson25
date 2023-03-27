(() => {
    'use strict';

    function BookCreateController(ApiService, $q, $location) {
        const $ctrl = this;
        $ctrl.book = {};
        $ctrl.bookForm = undefined;

        $ctrl.$onInit = () => {
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

        $ctrl.submitForm = () => {
            if ($ctrl.bookForm.$invalid) {
                alert('INVALID FORM');
                return;
            }

            ApiService.createBook($ctrl.book).then(() => {
                $location.path('/store');
            }).catch((error) => {
                console.error('error', error);

                alert('Error: ' + error.message || 'unknown error');
            });
        };
    }

    BookCreateController.$inject = ['ApiService', '$q', '$location'];

    angular
        .module('market.books')
        .component('appBookCreate', {
            templateUrl: 'app/features/books/components/book-create/book-create.component.html',
            controller: BookCreateController,
        });
})();
