angular.module('core.contentgroup', ['ngResource'])

angular.module('core.contentgroup').
service('ContentGroup', function($resource, $httpParamSerializerJQLike) {
    return $resource('/contentgroup/:id', {}, {
        hotest: {
            method: 'GET',
            isArray: true,
            params: {
                number: 6
            },
            url: '/contentgroup/hotest'
        },
        hotestOfCategory: {
            method: 'GET',
            isArray: true,
            params: {
                number: 6
            },
            url: '/contentgroup/hotest/ofcategory'
        },
        ofAuthor: {
            method: 'GET',
            isArray: true,
            url: '/contentgroup/ofauthor/:authorId'
        },
        ofTag: {
            method: 'GET',
            isArray: true,
            url: '/contentgroup/oftag/:tagId'
        },
        ofSubscriber: {
            method: 'GET',
            isArray: true,
            url: '/contentgroup/ofsubscriber/:subscriberId'
        },
        ofSubscribedByCurrentUser: {
            method: 'GET',
            isArray: true,
            url: '/contentgroup/ofsubscriber/currentuser'
        },
        ofCategory:{
          method:'GET',
          isArray:true,
          url:'/contentgroup/ofcategory/:categoryId'
        },
        subscribe: {
            method: 'POST',
            url: '/contentgroup/subscribe',
            headers:{'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest:$httpParamSerializerJQLike
        },
        unsubscribe: {
            method: 'POST',
            url: '/contentgroup/unsubscribe',
            headers:{'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest:$httpParamSerializerJQLike
        }
    })
})
