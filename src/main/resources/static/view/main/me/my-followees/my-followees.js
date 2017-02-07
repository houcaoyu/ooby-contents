angular.module('main.me.followees', ['core.user','core.loginout']).
  controller('myFolloweesCtrl',function(User,LoginOut,$scope){

      $scope.users=User.ofFllower({followerId:LoginOut.user.id})

  })
