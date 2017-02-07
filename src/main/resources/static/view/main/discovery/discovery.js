angular.module('main.discovery', ['ui.router','main.discovery.hot','main.discovery.category','main.discovery.rank']).
  controller('discoveryCtrl',function($scope,$state){
      // $scope.$state=$state
      // if($state.current.name=='discovery')
      //   $state.go('discovery.hot')
    }).
  config(function($stateProvider,$urlRouterProvider) {
    var states=[
      {
        name: 'main.discovery.hot',
        url: '/hot',
        templateUrl: '/view/main/discovery/hot/hot.template.html',
        controller:'hotCtrl'
      },
      {
        name: 'main.discovery.category',
        url: '/category',
        templateUrl:'/view/main/discovery/category/category.template.html',
        controller:'hotCategoryCtrl'
      },
      {
        name: 'main.discovery.rank',
        url: '/rank',
        templateUrl:'/view/main/discovery/rank/rank.template.html',
        controller:'rankCtrl'
      }
    ]

      angular.forEach(states, function(state) {
        $stateProvider.state(state)
      })
    })
