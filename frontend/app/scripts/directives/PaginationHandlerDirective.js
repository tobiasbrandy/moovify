'use strict';
define(['frontend'], function(frontend) {

  frontend.directive('paginationHandlerDirective', function() {

    return {
      restrict: 'E',
      scope: {
        paginationParams: '=',
        searchFn: '&',
        resetPaginationFn: '='
      },

      templateUrl: 'resources/views/directives/paginationHandlerDirective.html',

      link: function(scope) {

        scope.searchFn = scope.searchFn();

        scope.resetPaginationFn = function() {
          scope.paginationParams.currentPage = 0;
        }

        scope.$watchCollection('paginationParams', function(newParams, oldParams, scope) {

          if(!newParams || !oldParams){
            return;
          }

          var newPageSize = newParams.pageSize !== oldParams.pageSize;
          var newPageNumber = newParams.currentPage !== oldParams.currentPage;

          if(newPageSize || newPageNumber){

            if(newPageSize){
              scope.resetPaginationFn();
            }

            console.log("search");

            scope.searchFn();
          }
        });

      }
    };
  });

});