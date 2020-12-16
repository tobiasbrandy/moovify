define(['angular', 'angularMocks', 'frontend', 'services/PostInteractionService', 'restangular'], function(angular) {

  describe('PostInteractionService', function() {

    var PostInteractionService;
    var $scope;
    var $q;
    var $httpBackend;
    var Restangular;
    var $provide;

    beforeEach(angular.mock.module('frontend'));

    beforeEach(module(function (_$provide_) {
      $provide = _$provide_;
    }));

    beforeEach(inject(function (_PostInteractionService_, _$rootScope_, _$q_, _$httpBackend_, _Restangular_) {
      PostInteractionService = _PostInteractionService_;
      $scope = _$rootScope_;
      $q = _$q_;
      $httpBackend = _$httpBackend_;
      Restangular = _Restangular_;
    }));

    beforeEach(function() {
      var ReqFullResponse = Restangular.withConfig(function (RestangularConfigurer) {
        RestangularConfigurer.setFullResponse(true);
      });

      $provide.value('RestFulResponse', {withAuth: function() {return $q.resolve(ReqFullResponse)}});

      $provide.value('LoggedUserFactory', {
        getLoggedUser: function() { return null; }
      });
    });

    it('send vote success test', function () {

      var value = 1;

      var post = {
        all: function (path) {
          return Restangular.all('/posts/1').all(path);
        },
        totalLikes: 0,
        userVote: -1
      }

      $httpBackend.expectPUT('http://localhost/api/posts/1/votes', {value: value}).respond(200);

      PostInteractionService.sendVote(post, value).then(function (post) {
        expect(post.totalLikes).toEqual(2);
        expect(post.userVote).toEqual(1);
      });

      $httpBackend.flush();

      $scope.$digest();
    });

    it('toggle bookmark false test', function () {

      $httpBackend.expectDELETE('http://localhost/api/user/bookmarked/1').respond(204);

      var post = {
        hasUserBookmarked: true,
        id: 1
      }

      PostInteractionService.toggleBookmark(post).then(function (post) {
        expect(post.hasUserBookmarked).toEqual(false);
      });

      $httpBackend.flush();

      $scope.$digest();
    });

    it('toggle bookmark true test', function () {

      $httpBackend.expectPUT('http://localhost/api/user/bookmarked/1').respond(204);

      var post = {
        hasUserBookmarked: false,
        id: 1
      }

      PostInteractionService.toggleBookmark(post).then(function (post) {
        expect(post.hasUserBookmarked).toEqual(true);
      });

      $httpBackend.flush();

      $scope.$digest();
    });
  });


});
