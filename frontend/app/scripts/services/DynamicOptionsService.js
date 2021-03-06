'use strict';
define(['frontend', 'services/utilities/RestFulResponseFactory'], function (frontend) {

  frontend.service('DynamicOptionsService', function ($q, RestFulResponse) {

    var optionsMap = {};

    return {
      getOptions: function(endPoint) {

        return $q(function(resolve, reject) {

          if(!optionsMap[endPoint]) {

            RestFulResponse.noAuth().all(endPoint).all('options').getList().then(function(response) {
              optionsMap[endPoint] = response.data.map(function(entry) { return entry.plain() });
              resolve(optionsMap[endPoint]);
            }).catch(reject);
          }
          else {
            resolve(optionsMap[endPoint]);
          }
        });
      }
    }

  });

});
