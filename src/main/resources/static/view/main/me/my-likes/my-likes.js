angular.module('main.me.likes', ['core.content','core.loginout']).
  controller('myLikesCtrl',function(Content,LoginOut,$scope){

      $scope.contents=Content.ofFan({fanId:LoginOut.user.id})

    $scope.title="我的赞"
  })
