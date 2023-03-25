(() => {
    'use strict';

    const BASE_URL = 'http://localhost:8080';
    const CONTEXT_PATH = `${BASE_URL}/api/v1`;
    const CONTEXT_PATH_BOOK = `${CONTEXT_PATH}/book`;

    function ApiService($http, $localStorage) {
        return {
            loadBooks: () => {
                return $http.get(CONTEXT_PATH_BOOK).then((response) => {
                    return response.data;
                });
            },
            deleteBook: (id) => {
                return $http.delete(CONTEXT_PATH_BOOK + '/' + id);
            },
            loadComments: (id) => {
                return $http.get(CONTEXT_PATH_BOOK + '/' + id + '/comment').then((response) => {
                    return response.data;
                });
            },
            updateBook: (book) => {
                return $http.put(CONTEXT_PATH_BOOK + '/' + book.id, book).then((response) => {
                    return response.data;
                });
            },
            createBook: (book) => {
                return $http.post(CONTEXT_PATH_BOOK, book).then((response) => {
                    return response.data;
                });
            },
            getBook: (id) => {
                return $http.get(CONTEXT_PATH_BOOK + '/' + id).then((response) => {
                    return response.data;
                });
            },
            getAuthors: () => {
                return $http.get(CONTEXT_PATH + '/author').then((response) => {
                    return response.data;
                });
            },
            getGenres: () => {
                return $http.get(CONTEXT_PATH + '/genre').then((response) => {
                    return response.data;
                });
            },
        };
    }

    ApiService.$inject = ['$http', '$localStorage'];

    angular
        .module('market')
        .factory('ApiService', ApiService);
})();