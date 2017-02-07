angular.module('main.moment.follow', ['core.contentGroup']).
  controller('followCtrl',function(ContentGroup,$scope){
    $scope.contentGroups=ContentGroup.ofSubscribedByCurrentUser()
  }).
