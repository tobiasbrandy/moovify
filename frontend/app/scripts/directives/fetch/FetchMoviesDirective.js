'use strict';
define(['frontend', 'services/LoginService', 'services/utilities/PageTitleService', 'services/entities/MovieService',
  'directives/PaginationHandlerDirective', 'directives/listEntries/MovieListEntryDirective'], function(frontend) {

  function init(value, defaultVal, loadFromUrl){

    if(loadFromUrl){
      return value ? value : defaultVal;
    }
    return defaultVal;
  }

  frontend.directive('fetchMoviesDirective', function($location, $routeParams) {
    return {

      restrict: 'E',

      scope: {
        order: '<',
        enabledEntities: '<',
        defaultPageSize: '<',
        loadFromUrl: '<',
        path: '@',
        refreshUrlFn: '='

      },

      controller: function ($scope, MovieService) {

        $scope.fetchMovies = function() {

          $scope.movies = null;

          MovieService.fetchMovies($scope.path, $scope.enabledEntities, $scope.order,
            $scope.paginationParams.pageSize, $scope.paginationParams.currentPage).then(

            function(resp) {
              $scope.movies = resp.collection;
              $scope.paginationParams = resp.paginationParams;
              $scope.queryParams = resp.queryParams;

              if($scope.firstSearchDone)
                $scope.refreshUrlFn();
              else
                $scope.firstSearchDone = true;

              $scope.paginationMutex = false;
            }

          ).catch(function() { $location.path('/500') });

        };

        $scope.refreshUrlFn = function() {
          if ($scope.queryParams !== null) {
            Object.keys($scope.queryParams)
              .forEach(function (paramKey) {
                $location.search(paramKey, $scope.queryParams[paramKey])
              });
          }
        };
      },

      link: function(scope) {

        scope.movies = null;

        scope.paginationMutex = false;

        scope.firstSearchDone = false;

        scope.paginationParams = {
          currentPage: init(parseInt($routeParams.pageNumber), 0, scope.loadFromUrl),
          pageSize: init(parseInt($routeParams.pageSize), scope.defaultPageSize, scope.loadFromUrl)
        };

        scope.resetPagination = null;

        scope.queryParams = null;

        // Execute first fetch
        scope.fetchUsers();

      },

      templateUrl: 'resources/views/directives/fetch/fetchMoviesDirective.html'
    };
  });

});
