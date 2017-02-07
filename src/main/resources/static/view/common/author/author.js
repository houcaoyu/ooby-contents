angular.module('common.author', ['core.user','ui.router']).
  controller('authorCtrl',function(User,$scope,$stateParams){
      
      User.get({id:$stateParams.authorId},function(user){
        $scope.user=user
      })

      $scope.toggleFollow=function(){
        if(!$scope.user.followed)
          User.follow({id:$scope.user.id},function(){
            $scope.user.followed=true
          })
        else
          User.unfollow({id:$scope.user.id},function(){
            $scope.user.followed=false
          })
      }
  })
