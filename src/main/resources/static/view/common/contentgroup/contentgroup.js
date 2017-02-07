angular.module('common.contentgroup', ['core.contentgroup','core.user','core.loginout','ui.router']).
  controller('contentGroupCtrl',function(ContentGroup,User,$scope,$stateParams,splitArrayFilter,LoginOut){

      $scope.tabView='detail'
      ContentGroup.get({id:$stateParams.contentGroupId},function(data){
        $scope.contentGroup=data
        $scope.contentGroup.splitedTags=splitArrayFilter($scope.contentGroup.tags,3)
        $scope.isOwn=LoginOut.user?LoginOut.user.id==$scope.contentGroup.user.id:false
      })
      $scope.subscribe=function(){
        ContentGroup.subscribe({id:$stateParams.contentGroupId},function(){
          $scope.contentGroup.subscribed=true;
        },function(rejection){
          console.log('aaa',rejection)
        })
      }
      $scope.unsubscribe=function(){
        ContentGroup.unsubscribe({id:$stateParams.contentGroupId},function(){
          $scope.contentGroup.subscribed=false;
        })
      }

      $scope.toggleFollow=function(){
        if(!$scope.contentGroup.user.followed)
          User.follow({id:$scope.contentGroup.user.id},function(){
            $scope.contentGroup.user.followed=true
          })
        else
          User.unfollow({id:$scope.contentGroup.user.id},function(){
            $scope.contentGroup.user.followed=false
          })
      }


  })
