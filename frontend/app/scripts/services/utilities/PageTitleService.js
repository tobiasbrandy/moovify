'use strict';
define(['frontend'], function(frontend) {

	frontend.service('PageTitle', function($translate) {
    var title = {
	    value: 'Moovify'
    };
	  return {
	    getTitle: function () {
	      return title;
      },
      setTitle: function (i18nKey, args) {
	      $translate(i18nKey, args).then(function(t) { title.value = t }).catch(function() { title.value = 'Moovify' });
      }
    }
	});
});
