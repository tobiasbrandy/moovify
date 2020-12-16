define(['frontend', 'uikit', 'directives/TabDisplayDirective', 'services/UpdateAvatarService',
    'services/utilities/RestFulResponseFactory', 'directives/fetch/FetchPostsDirective',
    'directives/fetch/FetchUsersDirective', 'directives/fetch/FetchCommentsDirective', 'services/LoginService',
    'services/UserService', 'services/utilities/PageTitleService']
  , function(frontend, UIkit) {

    'use strict';
    frontend.controller('profileCtrl', function($scope, $locale, $translate, $location, $routeParams, UpdateAvatar,
                                                RestFulResponse, LoggedUserFactory, UserService, PageTitle, $q) {

      var routeID = parseInt($routeParams.id);
      $scope.user = {};
      $scope.loadUserFinished = false;
      $scope.isFollowed = false;

      if(routeID) {
        if(routeID !== $scope.loggedUser.id) {
          var getUserData = $q(function (resolve, reject) {
            RestFulResponse.noAuth().one('/users/' + routeID).get().then(function (r) {
              if(r.data.enabled === false) throw '';
              Object.assign($scope.user, r.data);
              $scope.isAdmin = UserService.userHasRole($scope.user, 'ADMIN');
              $scope.tabs = [
                {value:'posts', message:"{{'USER_POST_TAB_DISPLAY' | translate}}"},
                {value:'comments', message:"{{'USER_COMMENTS_TAB_DISPLAY' | translate}}"},
                {value:'bookmarks', message:"{{'USER_BOOK_TAB_DISPLAY' | translate}}"},
                {value:'following', message:"{{'USER_FOLLOWED_USERS' | translate}}"},
              ];
              PageTitle.setTitle('PROFILE_TITLE', {user:$scope.user.username});
              resolve();
            }).catch(function (err) {
              console.log(err);
              $location.path('/404');
              reject();
            })
          });

          var followedUser = $q(function (resolve, reject){
            RestFulResponse.withAuth($scope.loggedUser).then(function (r) {
              r.one('user/following/'+routeID).get().then(function (resp) {
                $scope.isFollowed = resp.data.response;
                resolve();
              }).catch(reject);
            }).catch(reject);
          })

          $q.all(getUserData, followedUser).then(function () {
            $scope.loadUserFinished = true;
          })

        } else {
          Object.assign($scope.user, $scope.loggedUser);
          PageTitle.setTitle('PROFILE_TITLE', {user:$scope.user.username});
          $scope.isAdmin = UserService.userHasRole($scope.loggedUser, 'ADMIN');
          $scope.loadUserFinished = true;
        }
      } else {
        Object.assign($scope.user, $scope.loggedUser);
        PageTitle.setTitle('PROFILE_TITLE', {user:$scope.user.username});
        $scope.isAdmin = UserService.userHasRole($scope.loggedUser, 'ADMIN');
        $scope.loadUserFinished = true;
      }


      $scope.profileSameAsLogged = function () {
        return $routeParams.id === undefined || routeID === $scope.loggedUser.id;
      }

      if(UserService.userHasRole($scope.loggedUser, 'NOT_VALIDATED')) {
        UIkit.modal(document.getElementById('confirm-email-profile-modal')).show();
      }

      $scope.tabs = [
        {value:'posts', message:"{{'PROFILE_POST_TAB_DISPLAY' | translate }}"},
        {value:'comments', message:"{{'PROFILE_COMMENTS_TAB_DISPLAY' | translate }}"},
        {value:'bookmarks', message:"{{'PROFILE_BOOK_TAB_DISPLAY' | translate }}"},
        {value:'following', message:"{{'PROFILE_FOLLOWED_USERS' | translate }}"},
      ];

      $scope.showing = {
        value: $routeParams.showing ? $routeParams.showing : $scope.tabs[0].value
      };

      $scope.setPostsUrl = null;
      $scope.setCommentsUrl = null;
      $scope.setBookmarksUrl = null;
      $scope.setFollowingUrl = null;

      $scope.$watch('showing.value', function(newParam, oldParam, scope) {

        if(newParam !== oldParam) {

          $location.search({ showing: scope.showing.value });

          if(newParam === 'posts' && scope.setPostsUrl !== null) {
            scope.setPostsUrl();
          }
          else if(newParam === 'comments' && scope.setCommentsUrl !== null) {
            scope.setCommentsUrl();
          }
          else if(newParam === 'bookmarks' && scope.setBookmarksUrl !== null) {
            scope.setBookmarksUrl();
          }
          else if(newParam === 'following' && scope.setFollowingUrl !== null) {
            scope.setFollowingUrl();
          }
        }

      }, true);

      if($locale.id === 'es') {
        $scope.followForms = {
          0: 'Seguidores',
          one: 'Seguidor',
          other: 'Seguidores'
        };
        $scope.voteForms = {
          0: 'Votos',
          one: 'Voto',
          other: 'Votos'
        };
      } else {
        $scope.followForms = {
          0: 'Followers',
          one: 'Follower',
          other: 'Followers'
        };
        $scope.voteForms = {
          0: 'Votes',
          one: 'Vote',
          other: 'Votes'
        };
      }

      $scope.nameConstrains = {
        pattern: /^[a-zA-Z ]*$/,
        minLen: 2,
        maxLen: 50
      }

      $scope.usernameConstrains = {
        pattern: /^[a-zA-Z0-9#_]+$/,
        minLen: 6,
        maxLen: 50
      }

      $scope.descriptionConstrains = {
        maxLen: 400
      }

      $scope.passwordConstrains = {
        pattern: /^[^\s]+$/,
        minLen: 12,
        maxLen: 30
      }

      var fieldErrors = {
        name: {
          i18nKey: 'USER_CREATE_NAME',
          message: ''
        },
        username: {
          i18nKey: 'USER_CREATE_USERNAME',
          message: ''
        },
        description: {
          i18nKey: 'USER_DESCRIPTION_PLACEHOLDER',
          message: ''
        },
      }

      $scope.editPass = {
        password: '',
        repeatPassword: ''
      };

      $scope.avatar = UpdateAvatar.getAvatar();
      var inputFile = angular.element(document.getElementById('avatar'))[0];

      inputFile.addEventListener('change', function () {
        UpdateAvatar.setFile(inputFile.files[0]);
        $scope.$apply();
        if(!$scope.avatar.error && $scope.avatar.file !== undefined) {
          UIkit.modal(document.getElementById('avatar-update-modal')).show();
        }
      });

      $scope.uploadAvatar = function () {
        UpdateAvatar.uploadAvatar().then(function () {
          $scope.user.avatar = $scope.loggedUser.avatar;
        });
        UIkit.modal(document.getElementById('avatar-update-modal')).hide();
      };

      $scope.updateUserInfo = function () {
        $scope.btnPressed = true;

        if(
          !$scope.fieldIsNotValid('editForm', 'name') &&
          !$scope.fieldIsNotValid('editForm', 'username') &&
          !$scope.descriptionIsNotValid()
        ) {
          $scope.loading = true;
          RestFulResponse.withAuth($scope.loggedUser).then(function (r) {
            var aux = {};
            Object.keys($scope.editInfo).forEach(function (key) {
              if($scope.editInfo[key] !== $scope.loggedUser[key]) {
                aux[key] = $scope.editInfo[key];
              }
            });
            if(Object.keys(aux).length > 0) {
              r.one('/user').customPUT(aux, undefined, undefined, {'Content-Type': 'application/json'})
               .then(function () {
                 $scope.loading = false;
                 Object.assign($scope.loggedUser, aux);
                 Object.assign($scope.user, aux);
                 $scope.btnPressed = false;
                 UIkit.modal(document.getElementById('edit-info-modal')).hide();
                 if(aux.username !== undefined) {
                   LoggedUserFactory.logout().then(function () {
                     $location.path('/login');
                   });
                 }
               })
               .catch(function (err) {
                 $scope.loading = false;
                 if(err.data) {
                   err.data.forEach(function (e) {
                     $translate(fieldErrors[e['attribute']].i18nKey).then(function(field) {
                       $translate('FORM_DUPLICATED_FIELD_ERROR', {field: field}).then(function(msg) {
                         fieldErrors[e['attribute']].message = msg;
                       }).catch(function(err) { console.log('inside', err) });
                     }).catch(function(err){ console.log('outside', err) });
                   });
                 } else {
                   console.log(err);
                 }
               });
            } else {
              $scope.loading = false;
              UIkit.modal(document.getElementById('edit-info-modal')).hide();
            }
          });
        }
      }

      $scope.fieldRequired = function (form, field) {
        return $scope.btnPressed && $scope[form][field].$error.required !== undefined;
      }

      // fieldRequired || cons1 || cons2 || ... || consN
      $scope.fieldIsNotValid = function (form, field) {
        return $scope.fieldRequired(form, field) || ($scope[form])[field].$error.pattern || ($scope[form])[field].$error.minlength || ($scope[form])[field].$error.maxlength;
      }

      $scope.descriptionIsNotValid = function () {
        return $scope.fieldRequired('editForm',"description") || $scope.editForm.description.$error.maxlength;
      }

      $scope.passwordsNotEquals = function () {
        return $scope.editPass.password !== $scope.editPass.repeatPassword && !($scope.editPass.password === undefined || $scope.editPass.repeatPassword === undefined);
      }

      $scope.checkFieldError = function (field) {
        if(fieldErrors[field] === undefined) {
          throw 'Field ' + field + 'does not exists!';
        }

        return fieldErrors[field].message !== '';
      }

      $scope.getFieldErrors = function (field) {
        return fieldErrors[field].message;
      }

      $scope.resetFieldErrors = function (field) {
        fieldErrors[field].message = '';
      }

      $scope.resetInfoModal = function () {
        $scope.editInfo = {
          name: $scope.loggedUser.name,
          username: $scope.loggedUser.username,
          description: $scope.loggedUser.description
        }

        $scope.btnPressed = false;
        $scope.loading = false;
      }

      $scope.updatePassword = function () {
        $scope.btnPressed = true;

        if(
          !$scope.fieldIsNotValid('changePassForm', 'password') &&
          !$scope.fieldIsNotValid('changePassForm', 'repeatPassword') &&
          !$scope.passwordsNotEquals()
        ) {
          $scope.loading = true;
          RestFulResponse.withAuth($scope.loggedUser).then(function (r) {
            delete $scope.editPass.repeatPassword
            r.one('/user').customPUT($scope.editPass, undefined, undefined, {'Content-Type': 'application/json'})
              .then(function () {
                $scope.loading = false;
                $scope.btnPressed = false;
                UIkit.modal(document.getElementById('change-password-modal')).hide();
                LoggedUserFactory.logout().then(function () {
                  $location.path('/login');
                });
              }).catch(function (err) {
              $scope.loading = false;
              console.log(err);
            })
          }).catch(function (err) {
            $scope.loading = false;
            console.log(err);
          });
        }
      }

      $scope.resetInfoModal();

      $scope.confirmEmail = {};
      $scope.tokenError = false;

      $scope.sendConfirmation = function () {
        $scope.btnPressed = true;
        if(
          !$scope.fieldRequired('confirmMailForm', 'token')
        ) {
          RestFulResponse.withAuth($scope.loggedUser).then(function (r) {
            r.all('/user/email_confirmation').customPUT($scope.confirmEmail, undefined, undefined, {'Content-Type': 'application/json'})
              .then(function () {
                var idx = $scope.loggedUser.roles.indexOf('NOT_VALIDATED');
                $scope.loggedUser.roles[idx] = 'USER';
                UIkit.modal(document.getElementById('confirm-email-profile-modal')).hide();
              })
              .catch(function (err) {
                $scope.tokenError = true;
                console.log(err);
              })
          })
        }
      }

      $scope.resendSuccess = false;
      $scope.resendError = false;

      $scope.resendEmail = function () {
        RestFulResponse.withAuth($scope.loggedUser).then(function (r) {
          r.all('/user/email_confirmation').post().then(function () {
            $scope.resendSuccess = true;
          }).catch(function (err) {
            $scope.resendError = true;
            console.log(err);
          })
        })
      }

      $scope.followUser = function () {
        RestFulResponse.withAuth($scope.loggedUser).then(function (r) {
          r.one('/user/following/'+routeID).put().then(function (){
            $scope.isFollowed = true;
            $scope.user.followerCount += 1;
          }).catch(console.log);
        }).catch(console.log);
      }
      $scope.unfollowUser = function () {
        RestFulResponse.withAuth($scope.loggedUser).then(function (r) {
          r.one('/user/following/'+routeID).remove().then(function (){
            $scope.isFollowed = false;
            $scope.user.followerCount -= 1;
          }).catch(console.log);
        }).catch(console.log);
      }

      $scope.promoteUser = function () {
        RestFulResponse.withAuth($scope.loggedUser).then(function (r) {
          r.one('/users/'+routeID+'/privilege').put().then(function () {
            $scope.isAdmin = true;
            UIkit.modal(document.getElementById('promote-modal')).hide();
          }).catch(console.log);
        }).catch(console.log);
      };

      $scope.deleteUser = function () {
        RestFulResponse.withAuth($scope.loggedUser).then(function (r) {
          r.one('/users/'+routeID+'/enabled').remove().then(function () {
            UIkit.modal(document.getElementById('delete-modal')).hide();
            $location.path('/404');
          }).catch(console.log);
        }).catch(console.log);
      }
    });
});
