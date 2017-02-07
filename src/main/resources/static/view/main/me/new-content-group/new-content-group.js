angular.module('main.me.newcontentgroup', ['core.contentgroup','core.category','ui.router','lr.upload']).
  controller('newContentGroupCtrl',function($scope,ContentGroup,Category,$state){
    var ctrl=this
    $scope.title="新建专辑"
    Category.query(function(categories){
      $scope.categories=categories
    })
    $scope.contentGroup={}
    $scope.submit=function(){
      $scope.contentGroup.categoryId=$scope.selectedCategory.id
      ContentGroup.save($scope.contentGroup,function(){
        alert('success')
        $state.back()
      })
    }

    $scope.onSuccess=function(response){
      $scope.contentGroup.coverId=response.data.id
      $scope.url='/file/temp/'+response.data.id
    }

    $scope.upload=function(){
      angular.element('.btn-upload input')[0].click();
    }
  }).
  controller('editContentGroupCtrl',function($scope,ContentGroup,Category,$state){
    $scope.title="编辑专辑"
    Category.query(function(categories){
      $scope.categories=categories
    })
    ContentGroup.get({id:$state.params.contentGroupId},function(contentGroup){
      $scope.contentGroup={
        id:contentGroup.id,
        name:contentGroup.name,
        description:contentGroup.description
      }
      $scope.selectedCategory={
        id:contentGroup.category.id,
        name:contentGroup.category.name
      }
      $scope.url='/file/contentgroup/'+contentGroup.id
    })
    $scope.submit=function(){
      $scope.contentGroup.categoryId=$scope.selectedCategory.id
      ContentGroup.update($scope.contentGroup,function(){
        alert('success')
        $state.back()
      })
    }

    $scope.onSuccess=function(response){
      $scope.contentGroup.coverId=response.data.id
      $scope.url='/file/temp/'+response.data.id
    }

    $scope.upload=function(){
      angular.element('.btn-upload input')[0].click();
    }
  })
