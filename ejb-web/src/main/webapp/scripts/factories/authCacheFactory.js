angular.module('bcFactories')
	.factory('authCacheFactory', ['$cacheFactory', function($cacheFactory) {
		var cache = $cacheFactory('auth-cache');

		return {
			getCache: function() {
				return cache.get('AUTH');
			},
			put: function(value) {
				cache.put('AUTH', value);
			},
			clearCache: function() {
				cache.remove('AUTH');
			}
		};

	}]);