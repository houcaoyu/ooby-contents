angular.module('core.user', ['ngResource']).
  service('User', function($resource) {
    return $resource('/user/:id',{},{
      hotest:{
        method:'GET',
        isArray:true,
        params:{number:6},
        url:'/user/hotest'
      },
      ofFllower:{
        method:'GET',
        isArray:true,
        url:'/user/offollower/:followerId'
      },
      follow:{
        method:'POST',
        url:'/user/follow'
      },
      unfollow:{
        method:'POST',
        url:'/user/unfollow'
      }
    })
  })
