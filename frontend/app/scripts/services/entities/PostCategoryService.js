'use strict';
define(['frontend', 'services/utilities/RestFulResponseFactory'], function (frontend) {

  frontend.service('PostCategoryService', function($q, RestFulResponse) {

    var categories = null;

    return {
      getPostCategories: function() {

        return $q(function(resolve, reject) {
          if (!categories) {
            RestFulResponse.noAuth().all('/posts').all('categories').getList().then(function(response) {
              categories = response.data.map(function(entry) { return entry.plain(); });
              resolve(categories);
            }).catch(reject);
          } else {
            resolve(categories)
          }
        });
      }
    }
  });
});
