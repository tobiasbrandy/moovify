define(['angular', 'angularMocks', 'frontend', 'services/PostCategoriesService', 'restangular'], function(angular) {

  describe('PostCategoriesService', function() {

    var $scope;
    var $q;
    var $httpBackend;
    var Restangular;
    var $provide;

    beforeEach(angular.mock.module('frontend'));

    beforeEach(module(function(_$provide_) {
      $provide = _$provide_;
    }))

    beforeEach(inject(function (_$rootScope_, _$q_, _$httpBackend_, _Restangular_) {
      $scope = _$rootScope_;
      $q = _$q_;
      $httpBackend = _$httpBackend_;
      Restangular = _Restangular_;
    }));

    beforeEach(function() {
      var ReqFullResponse = Restangular.withConfig(function (RestangularConfigurer) {
        RestangularConfigurer.setFullResponse(true);
      });

      $provide.value('RestFulResponse', {noAuth: function() { return ReqFullResponse }});
    });

    it('get post categories test', inject(function(PostCategoriesService){

      var categories = [];

      for (var i = 0; i < 5; i++) {
        var category = {};
        category[i] = i;
        category.plain = function () {
        };
        categories.push(category);
      }

      $httpBackend.expectGET(/.*\/api\/posts\/categories/).respond(204, categories)

      PostCategoriesService.getPostCategories().then(function (returnedCategories) {
        expect(returnedCategories.map(function(c) { return c.originalElement })).toEqual(categories);
      });

      $httpBackend.flush();

      $scope.$digest();
    }));
  });

});