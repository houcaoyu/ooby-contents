angular.module('main.me.subscriptions', ['core.contentgroup','core.loginout']).
  controller('mySubscriptionsCtrl',function(ContentGroup,LoginOut,$scope){
    $scope.title="我的订阅"

      $scope.contentGroups=ContentGroup.ofSubscriber({subscriberId:LoginOut.user.id})


  })
