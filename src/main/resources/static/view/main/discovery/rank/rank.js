angular.module('main.discovery.rank', ['core.contentgroup','core.user','core.content']).
  controller('rankCtrl',function($scope,ContentGroup,User,Content){
    $scope.contentGroups=ContentGroup.hotest()
    $scope.users=User.hotest()
    $scope.contents=Content.hotest()
  })
