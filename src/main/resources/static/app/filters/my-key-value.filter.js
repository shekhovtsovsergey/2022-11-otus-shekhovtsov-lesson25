(() => {
    'use strict';

    function MyKeyValueFilter() {
        return (id, list) => {
            const found = list.filter(item => item.id === id);

            return (found.length) ? found[0].name : id;
        };
    }

    angular
        .module('market')
        .filter('myKeyValue', MyKeyValueFilter);
})();