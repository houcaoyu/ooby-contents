angular.module('core.comment', ['ngResource'])

angular.module('core.comment').
service('Comment', function($resource) {
  return $resource('/comment/:id',{},{})
})
