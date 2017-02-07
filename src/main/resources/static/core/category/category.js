angular.module('core.category', ['ngResource'])
angular.module('core.category').
service('Category', function($resource) {
    return $resource('/cgc/:id', {}, {})
})
