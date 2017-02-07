angular.module('main.me', ['core.user','core.loginout','ui.router','main.me.contentgroups','main.me.subscriptions','main.me.collections','main.me.likes','main.me.followees','main.me.newcontentgroup','main.me.newcontent']).
  controller('meCtrl',function($scope,LoginOut,User,$state){
    // $scope.user=User.get({id:LoginOut.user.id})

      $scope.user=User.get({id:LoginOut.user.id})

    $scope.logout=function(){
      LoginOut.logout().then(function(){
        alert('logouted')
        $state.go('main.discovery')
      })
    }
  })
