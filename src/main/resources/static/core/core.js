angular.module('core', ['core.contentgroup','core.user','core.category','core.loginout',
    'core.content','core.category','core.comment','ui.router']).
  config(function($resourceProvider,$httpParamSerializerJQLikeProvider,$httpProvider){
    $resourceProvider.defaults.actions.save.headers={'Content-Type': 'application/x-www-form-urlencoded'}
    $resourceProvider.defaults.actions.save.transformRequest=$httpParamSerializerJQLikeProvider.$get()

    $resourceProvider.defaults.actions.update={
      method: 'PUT',
      headers:{'Content-Type': 'application/x-www-form-urlencoded'},
      transformRequest:$httpParamSerializerJQLikeProvider.$get()
    }
    $httpProvider.interceptors.push('httpInterceptor')
    angular.forEach($httpProvider.defaults.headers,function(item){
      angular.extend(item,{'Content-Type': 'application/x-www-form-urlencoded'})
    })
    $httpProvider.defaults.transformRequest=$httpParamSerializerJQLikeProvider.$get()

  }).
    factory('httpInterceptor',function($q,$injector){
      return {
        responseError:function(rejection){
          //console.log(rejection)
          var $state=$injector.get('$state')
          if(rejection.status==401){
            // var currentState=$state.current.name
            $injector.get('LoginOut').afterLoginSuccess=function(){
              // $state.go(currentState)
              history.back(-1)
            }
            $state.go('login')
            //return 
          }
          return $q.reject(rejection);
        }
      }
    })
