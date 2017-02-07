angular.module('common.login', ['core.loginout','ui.router']).
  component('login',{
    templateUrl:'/view/common/login/login.template.html',
    controller:function($scope,LoginOut){

      $scope.login=function(){
        LoginOut.login($scope.username,$scope.password).then(function(){
          alert('login successed');
          var callback=LoginOut.afterLoginSuccess
          LoginOut.afterLoginSuccess=null;
          (callback ||angular.identity)();
        })
      }
    }
  })
