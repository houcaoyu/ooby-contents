var app = angular.module('ooby.contents', ['ui.router', 'ngAnimate', 'core', 'main', 'common'])

app.controller('test', function($scope, $state) {
    $scope.$state = $state
})

app.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/main/discovery/hot');
    var states = [{
            name: 'main',
            url: '/main',
            abstract: true,
            templateUrl: '/view/main/main.template.html',
            controller: 'mainCtrl'
        },
        {
            name: 'contentgroup',
            url: '/contentgroup/{contentGroupId}',
            templateUrl: '/view/common/contentgroup/contentgroup.template.html',
            controller: 'contentGroupCtrl'
        },
        {
            name: 'category',
            url: '/category/{categoryId}',
            templateUrl: '/view/common/category/category.template.html',
            controller: 'categoryCtrl'
        },
        {
            name: 'author',
            url: '/author/{authorId}',
            templateUrl: '/view/common/author/author.template.html',
            controller: 'authorCtrl'
        },
        {
            name: 'content',
            url: '/content/{contentId}',
            templateUrl: '/view/common/content/content.template.html',
            controller: 'contentCtrl'
        },
        {
            name: 'login',
            url: '/login',
            template: '<login></login>'
        },
        {
          name: 'mycontentgroups',
          url: '/mycontentgroups',
          templateUrl: '/view/main/me/my-content-groups/my-content-groups.template.html',
          controller:'myContentGroupsCtrl'
        },
        {
          name: 'mysubscriptions',
          url: '/mysubscriptions',
          templateUrl: '/view/main/me/my-content-groups/my-content-groups.template.html',
          controller:'mySubscriptionsCtrl'
        },
        {
          name: 'mycollections',
          url: '/mycollections',
          templateUrl: '/view/main/me/my-collections/my-collections.template.html',
          controller:'myCollectionsCtrl'
        },
        {
          name: 'mylikes',
          url: '/mylikes',
          templateUrl: '/view/main/me/my-collections/my-collections.template.html',
          controller:'myLikesCtrl'
        },
        {
          name: 'myfollowees',
          url: '/myfollowees',
          templateUrl: '/view/main/me/my-followees/my-followees.template.html',
          controller:'myFolloweesCtrl'
        },
        {
          name: 'newcontentgroup',
          url: '/newcontentgroup',
          templateUrl: '/view/main/me/new-content-group/new-content-group.template.html',
          controller:'newContentGroupCtrl'
        },
        {
          name: 'editcontentgroup',
          url: '/editcontentgroup/{contentGroupId}',
          templateUrl: '/view/main/me/new-content-group/new-content-group.template.html',
          controller:'editContentGroupCtrl'
        },
        {
          name: 'newcontent',
          url: '/newcontent',
          templateUrl: '/view/main/me/new-content/new-content.template.html',
          controller:'newContentCtrl',
          params:{
            contentGroupId:null
          }
        },
        {
          name: 'editcontent',
          url: '/editcontent/{contentId}',
          templateUrl: '/view/main/me/new-content/new-content.template.html',
          controller:'editContentCtrl'
        }
    ]

    angular.forEach(states, function(state) {
        $stateProvider.state(state)
    })
});
app.decorator('$state',function($delegate,$transitions){
  var $state=$delegate
  $transitions.onSuccess({},function(transition){
    $state.previousState=transition.$from()
  })
  $state.back=function(){
    //$state.go($state.previousState.name,$state.previousState.params,{inherit:true,relative:"contentgroup",source:"sref"})
    history.back(-1)
  }
  return $state;
})
app.run(function($transitions,$rootScope,$animate,$state,LoginOut) {
    $rootScope.setAnimateType=function(animateType){
      $rootScope.animateType=animateType
    }
    $rootScope.$state=$state
    $animate.on('enter',$('body'),function(element,phase){
      // console.log('element',element)
      // console.log('phase',phase)
      if(phase=='close')
        $rootScope.setAnimateType('')
    })
    $.ajax({
      type:'POST',
      url:'/common/logininfo',
      async:false,
      success:function(user){
        LoginOut.user=user
      }
    })
    var matcher=function(state){
      var needLogin=['main.me','mycontentgroups','mysubscriptions',
        'mycollections','mylikes','myfollowees','newcontentgroup',
        'editcontentgroup','newcontent','editcontent']
      var result=false;
      angular.forEach(needLogin,function(name){
        if(result)
          return
        if(name==state.name){
          result=true
        }
      })
      return result;
    }
    $transitions.onBefore({to:matcher},function(trans){
      if(!LoginOut.user){
        LoginOut.afterLoginSuccess=function(){
          $state.go(trans.$to().name,trans.$to().params)
        }
        return $state.target('login')
      }
    })
    $rootScope.defaultState={
      discovery:'main.discovery.hot'
    }
    $transitions.onBefore({to:'main.discovery'},function(trans){
      return $state.target($rootScope.defaultState.discovery)
    })
    $transitions.onFinish({to:function(state){
      if(state.name.match(/^main\.discovery\./))
        return true
    }},function(trans){
      return $rootScope.defaultState.discovery=trans.$to().name
    })

})
