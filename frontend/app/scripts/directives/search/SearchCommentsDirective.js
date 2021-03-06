'use strict';
define(['frontend', 'services/entities/CommentService', 'directives/PaginationHandlerDirective',
  'directives/listEntries/CommentListEntryDirective', 'directives/search/CommentsFiltersHandlerDirective'], function (frontend) {

  function init(value, defaultVal, loadFromUrl){

    if(loadFromUrl){
      return value ? value : defaultVal;
    }
    return defaultVal;
  }

  frontend.directive('searchCommentsDirective', function ($location, $routeParams) {
    return {
      restrict: 'E',
      scope: {
        query: '=',
        enabledEntities: '<',
        refreshUrlFn: '=',
        defaultPageSize: '<',
        loadFromUrl: '<',
        adminControls: '<'
      },
      link: function (scope) {

        scope.comments = null;

        scope.paginationMutex = false;

        scope.firstSearchDone = false;

        scope.paginationParams = {
          currentPage: init(parseInt($routeParams.pageNumber), 0, scope.loadFromUrl),
          pageSize: init(parseInt($routeParams.pageSize), scope.defaultPageSize, scope.loadFromUrl)
        };

        scope.query = scope.$parent.query;

        scope.filterParams = {
          orderBy: init($routeParams.orderBy, 'newest'),
          enabled: scope.enabledEntities
        };

        scope.resetPagination = null;

        scope.queryParams = null;

        scope.$watch('query.value', function (newParam, oldParam, scope) {

          if (newParam === oldParam)
            return;

          if (scope.resetPagination)
            scope.resetPagination();

          scope.execSearch();
        }, true);

        scope.$watchCollection('filterParams', function (newParams, oldParams, scope) {

          var newOrderBy = newParams.orderBy !== oldParams.orderBy;

          if (newOrderBy) {

            if (scope.resetPagination)
              scope.resetPagination();

            scope.execSearch();
          }
        });

        // Execute first search
        scope.execSearch();
      },
      controller: function ($scope, CommentService) {
        $scope.execSearch = function () {

          $scope.comments = null;

          CommentService.searchComments(
            $scope.query.value, $scope.filterParams.enabled, $scope.filterParams.orderBy,
            $scope.paginationParams.pageSize, $scope.paginationParams.currentPage).then(

            function (resp) {
              $scope.comments = resp.collection;
              $scope.paginationParams = resp.paginationParams;
              $scope.queryParams = resp.queryParams

              if ($scope.firstSearchDone)
                $scope.refreshUrlFn();
              else
                $scope.firstSearchDone = true;

              $scope.paginationMutex = false;
            }
          ).catch(function () {
            $location.path('/500')
          });
        };

        $scope.refreshUrlFn = function () {
          if ($scope.queryParams !== null) {
            Object.keys($scope.queryParams)
              .forEach(function (paramKey) {
                $location.search(paramKey, $scope.queryParams[paramKey])
              });
          }
        };

        $scope.removeComment = function (comment) {
          var index = $scope.comments.indexOf(comment);
          if (index > -1) {
            $scope.comments.splice(index, 1);
          }
        };
      },
      templateUrl: 'resources/views/directives/search/searchCommentsDirective.html'
    };
  });
});
