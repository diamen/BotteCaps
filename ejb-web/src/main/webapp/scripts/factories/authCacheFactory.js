angular.module('bcFactories')
	.factory('authCacheFactory', ['$cacheFactory', function($cacheFactory) {
		return $cacheFactory('auth-cache');
	}]);