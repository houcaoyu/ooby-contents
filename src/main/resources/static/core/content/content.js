angular.module('core.content', ['ngResource'])

angular.module('core.content').
service('Content', function($resource) {
    return $resource('/content/:id', {}, {
        hotest: {
            method: 'GET',
            isArray: true,
            params: {
                number: 6
            },
            url: '/content/hotest'
        },
        ofAuthor: {
            method: 'GET',
            isArray: true,
            url: '/content/ofauthor/:authorId'
        },
        ofCollector: {
            method: 'GET',
            isArray: true,
            url: '/content/ofcollector/:collectorId'
        },
        ofFan: {
            method: 'GET',
            isArray: true,
            url: '/content/offan/:fanId'
        },
        ofReader: {
            method: 'GET',
            isArray: true,
            url: '/content/ofreader/:readerId'
        },
        like: {
            method: 'POST',
            url: '/content/like'
        },
        unlike: {
            method: 'POST',
            url: '/content/unlike'
        },
        collect: {
            method: 'POST',
            url: '/content/collect'
        },
        uncollect: {
            method: 'POST',
            url: '/content/uncollect'
        }
    })
})
