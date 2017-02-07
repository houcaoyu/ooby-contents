angular.module('core.loginout', []).
  service('LoginOut', function($http,$q) {
    return {
      afterLoginSuccess:undefined,
      user:null,
      login:function(username,password){
        var service=this;
        return $http.post('/login',{username:username,password:password}).then(function(response){
          service.user=response.data
        })
      },
      logout:function(){
        var service=this;
        return $http.post('/logout').then(function(){
          service.user=null
        })
      }
      // getLoginInfo:function(){
      //   var deferred = $q.defer();
      //   var service=this;
      //   this.user=deferred.promise
      //   $http.post('/common/logininfo').then(function(response){
      //     if(response.data)
      //       service.isLogined=true
      //     deferred.resolve(response.data)
      //   })
      // }

    }
  })
