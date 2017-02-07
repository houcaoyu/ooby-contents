angular.module('core').
	filter('splitArray', function() {
	    return function(input,lengthofsublist) {
				//return ''
	      if (angular.isUndefined(input)||input.length==0)
					return
				var resultList=[];
				var subList;
				for(var i=0;i<input.length;i++){
					if((i+1)%lengthofsublist==1){
						subList=[];
						resultList.push(subList)
					}
					subList.push(input[i])
				}
				return resultList;
	    };
	  });
