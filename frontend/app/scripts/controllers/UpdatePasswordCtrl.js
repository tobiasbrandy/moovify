define(['frontend', 'services/utilities/RestFulResponseFactory', 'services/utilities/PageTitleService', 'services/entities/UserService'], function(frontend) {

    'use strict';
    frontend.controller('UpdatePasswordCtrl', function($scope, RestFulResponse, UserService, PageTitle, $location, $timeout) {
      PageTitle.setTitle('UPDATE_PASSWORD_TITLE');
      $scope.newPass = {};
      $scope.btnPressed = false;

      $scope.valid = false;
      $scope.error = false;

      $scope.passwordConstrains = {
        pattern: /^[^\s]+$/,
        minLen: 12,
        maxLen: 30
      }

      $scope.fieldRequired = function (field) {
        return $scope.btnPressed && $scope.resetPassForm[field].$error.required !== undefined;
      }

      // fieldRequired || cons1 || cons2 || ... || consN
      $scope.fieldIsNotValid = function (field) {
        return $scope.fieldRequired(field) || $scope.resetPassForm[field].$error.pattern || $scope.resetPassForm[field].$error.minlength || $scope.resetPassForm[field].$error.maxlength;
      }

      $scope.passwordsNotEquals = function () {
        return $scope.newPass.password !== $scope.newPass.repeatPassword && !($scope.newPass.password === undefined || $scope.newPass.repeatPassword === undefined);
      }

      $scope.resetPass = function () {
        $scope.btnPressed = true;

      }

      $scope.changePass = function () {
        $scope.btnPressed = true;

        if(
          !$scope.fieldIsNotValid('token') &&
          !$scope.fieldIsNotValid('password') &&
          !$scope.fieldIsNotValid('repeatPassword') &&
          !$scope.passwordsNotEquals()
        ) {

          var passWithToken = {token: $scope.newPass.token, password: $scope.newPass.password};
          UserService.resetPassword(passWithToken)
            .then(function () {
              $scope.valid = true;
              $timeout(function () {
                $location.path('/login');
              }, 3000)
          }).catch(function (err) {
            $scope.error = true;
          });
        }
      }


    });

});
