angular.module('main.moment.subscribe', ['core.contentGroup']).
  controller('subscribeCtrl',function(ContentGroup,$scope){
    $scope.contentGroups=ContentGroup.ofSubscribedByCurrentUser()
  }).
