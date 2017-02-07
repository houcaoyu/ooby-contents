angular.module('main.me.collections', ['core.content','core.loginout']).
  controller('myCollectionsCtrl',function(Content,LoginOut,$scope){

      $scope.contents=Content.ofCollector({collectorId:LoginOut.user.id})

    $scope.title="我的收藏"

  })
