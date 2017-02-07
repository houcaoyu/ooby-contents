angular.module('main.me.contentgroups', ['core.contentgroup','core.loginout']).
  controller('myContentGroupsCtrl',function(ContentGroup,LoginOut,$scope){
    $scope.title="我的专辑"

      $scope.contentGroups=ContentGroup.ofAuthor({authorId:LoginOut.user.id})


  })
