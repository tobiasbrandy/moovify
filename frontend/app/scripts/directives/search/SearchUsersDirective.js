'use strict';
define(['frontend', 'services/LoginService', 'services/utilities/PageTitleService', 'services/entities/UserService',
  'directives/PaginationHandlerDirective', 'directives/search/UserFiltersHandlerDirective', 'directives/listEntries/UserListEntryDirective'], function(frontend) {

  function init(value, defaultVal, loadFromUrl){

    if(loadFromUrl){
      return value ? value : defaultVal;
    }
    return defaultVal;
  }

  frontend.directive('searchUsersDirective', function($location, $routeParams) {
    return {

      restrict: 'E',
      scope: {
        query: '=',
        enabledEntities: '<',
        refreshUrlFn: '=',
        defaultPageSize: '<',
        loadFromUrl: '<',
        adminControls:'<'
      },
      link: function(scope) {

        scope.users = null;

        scope.paginationMutex = false;

        scope.firstSearchDone = false;

        scope.paginationParams = {
          currentPage: init(parseInt($routeParams.pageNumber), 0, scope.loadFromUrl),
          pageSize: init(parseInt($routeParams.pageSize), scope.defaultPageSize, scope.loadFromUrl)
        };

        scope.query = scope.$parent.query;

        scope.filterParams = {
          role: init($routeParams.role, null),
          orderBy: init($routeParams.orderBy, 'newest'),
          enabled: scope.enabledEntities
        };

        scope.resetPagination = null;

        scope.queryParams = null;

        scope.$watch('query.value', function(newParam, oldParam, scope) {

          if(newParam === oldParam)
            return;

          if(scope.resetPagination)
            scope.resetPagination();

          scope.execSearch();
        }, true);

        scope.$watchCollection('filterParams', function(newParams, oldParams, scope) {

          var newRole = newParams.role !== oldParams.role;
          var newOrderBy = newParams.orderBy !== oldParams.orderBy;

          if(newRole || newOrderBy) {

            if(scope.resetPagination)
              scope.resetPagination();

            scope.execSearch();
          }

          // Execute first search
          scope.execSearch();

        });

      },

      controller: function ($scope, UserService) {

        $scope.execSearch = function() {

          $scope.users = null;

          UserService.searchUsers(
            $scope.query.value, $scope.filterParams.role, $scope.filterParams.enabled, $scope.filterParams.orderBy,
            $scope.paginationParams.pageSize, $scope.paginationParams.currentPage).then(

            function(resp) {
              $scope.users = resp.collection;
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

        $scope.removeUser = function (user) {
          var index = $scope.users.indexOf(user);
          if (index > -1) {
            $scope.users.splice(index, 1);
          }
        }

      },
      templateUrl: 'resources/views/directives/search/searchUsersDirective.html'
    };
  });

});
