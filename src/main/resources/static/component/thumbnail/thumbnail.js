angular.module('component.thumbnail', []).
  component('thumbnail',{
    templateUrl:'/component/thumbnail/thumbnail.template.html',
    controller:function($scope){
      this.$onInit=function(){

        if(this.type=='contentgroup')
          this.url='/file/contentgroup/'
        else if(this.type=='content'){
          this.url='/file/content/'
        }else{
          this.url='/file/user/'
          this.userStyle="border-radius:100%;background-size:cover;"
        }
        this.url+=this.id
      }



    },
    bindings: {
      type: '=',
      id:'='
    }
  })
