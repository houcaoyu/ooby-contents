angular.module('main.moment', ['main.moment.follow','main.moment.subscribe']).
  controller('momentCtrl',function(){
    $scope.$state=$state
  }).
  config(function($stateProvider,$urlRouterProvider) {
    var states=[
      {
        name: 'main.moment.follow',
        url: '/follow',
        templateUrl: '/view/main/moment/follow/follow.template.html',
        controller:'followCtrl'
      },
      {
        name: 'main.moment.subscribe',
        url: '/subscribe',
        templateUrl:'/view/main/moment/subscribe/subscribe.template.html',
        controller:'subscribeCtrl'
      }
    ]

      angular.forEach(states, function(state) {
        $stateProvider.state(state)
      })
    })
