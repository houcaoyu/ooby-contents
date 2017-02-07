angular.module('common.category', ['core.contentgroup','core.category','ui.router']).
  controller('categoryCtrl',function(ContentGroup,Category,$scope,$stateParams,splitArrayFilter){
    $scope.category=Category.get({id:$stateParams.categoryId})

    $scope.contentGroups=ContentGroup.ofCategory({categoryId:$stateParams.categoryId})
  })
