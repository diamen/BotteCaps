angular.module('bcFactories')

	.factory('validateFactory', ['$http', '$cacheFactory', '$sessionStorage', 'authCacheFactory', function($http, $cacheFactory, $sessionStorage, authCacheFactory) {
		var cache = $cacheFactory('httpOnce');

		var httpValid = function(authToken) {
			return $http({
				method: "GET",
				url: "./rest/auth/validate",
				headers: { 'AUTH-TOKEN': authToken }
			}).then(function(response) {
				cache.remove('validateRequest');
				authCacheFactory.put('AUTH', $sessionStorage.authToken);
				return response.data;
			});
		};

		return {
			isValid: function(authToken) {
				return cache.get('validateRequest') || cache.put('validateRequest',
						httpValid(authToken));
			}
		};

	}]);