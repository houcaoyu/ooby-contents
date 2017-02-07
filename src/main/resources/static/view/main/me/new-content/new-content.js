angular.module('main.me.newcontent', ['core.content','ui.router','lr.upload']).
  controller('newContentCtrl',function($scope,Content,$state){
    $scope.title='新建内容'
    $scope.content={
      contentGroupId:$state.params.contentGroupId
    }

    $scope.submit=function(){
      Content.save($scope.content,function(){
        alert('success')
        $state.back()
      })
    }

    $scope.onSuccess=function(response){
      $scope.content.coverId=response.data.id
      $scope.url='/file/temp/'+response.data.id
    }

    $scope.upload=function(){
      angular.element('.btn-upload input')[0].click();
    }
  }).
  controller('editContentCtrl',function($scope,Content,$state){
    $scope.title='编辑内容'
    Content.get({id:$state.params.contentId},function(content){
      $scope.content={
        id:content.id,
        title:content.title,
        text:content.text
      }
      $scope.url='/file/content/'+content.id
    })
    $scope.submit=function(){
      Content.update($scope.content,function(){
        alert('success')
        $state.back()
      })
    }

    $scope.onSuccess=function(response){
      $scope.content.coverId=response.data.id
      $scope.url='/file/temp/'+response.data.id
    }

    $scope.upload=function(){
      angular.element('.btn-upload input')[0].click();
    }
  })
