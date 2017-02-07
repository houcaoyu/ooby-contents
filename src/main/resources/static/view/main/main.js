angular.module('main', ['ui.router','main.discovery','main.me']).
  controller('mainCtrl',function($scope,$state){
      $scope.$state=$state
  }).
  config(function($stateProvider,$urlRouterProvider){
    var states=[
      {
        name: 'main.discovery',
        url: '/discovery',
        templateUrl: '/view/main/discovery/discovery.template.html',
        controller:'discoveryCtrl'
      },
      // {
      //   name: 'main.moment',
      //   url: '/moment',
      //   templateUrl: '/view/main/moment/moment.template.html',
      //   controller:'momentCtrl'
      // },
      {
        name: 'main.message',
        url: '/message',
        template: '<div>message</div>',
      },
      {
        name: 'main.me',
        url: '/me',
        templateUrl: '/view/main/me/me.template.html',
        controller:'meCtrl'
      }
    ]

      angular.forEach(states, function(state) {
        $stateProvider.state(state)
      })
  })
