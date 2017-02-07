angular.module('component.categoryBlock', ['core.contentgroup','core.category','ui.router','component.thumbnail']).
  component('categoryBlock',{
    templateUrl:'/component/category-block/category-block.template.html',
    controller:function(ContentGroup,Category,$scope,$rootScope){
      var ctrl=this
      $scope.init=function(){
        //get category base info
        $scope.category=Category.get({id:ctrl.categoryId})

        //get content groups
        var contentGroups=  ContentGroup.hotestOfCategory({categoryId:ctrl.categoryId,number:ctrl.number},function(list){
          var lengthofsublist=3
          var resultList=[]
          var subList
          for(var i=0;i<list.length;i++){
            if((i+1)%lengthofsublist==1){
              subList=[];
              resultList.push(subList)
            }
            subList.push(list[i])
          }
          $scope.contentGroups=resultList
        })
      }
      $scope.setAnimateType=$rootScope.setAnimateType

    },
    bindings: {
      categoryId: '=',
      number:'='
    }
  })
