angular.module('bcDirectives')
	.directive("isAdmin", ['$sessionStorage', '$rootScope', 'authCacheFactory', 'validateFactory', function($sessionStorage, $rootScope, authCacheFactory, validateFactory) {
		return {
			restrict: 'A',
			link: function(scope, element) {

				$rootScope.$watch('$storage.authToken', function() {

					if($sessionStorage.authToken === undefined) {
						element.css('display', 'none');
						return;
					} else {

						if(authCacheFactory.getCache()) {
							element.css('display', 'initial');
							return;
						}

						validateFactory.isValid($sessionStorage.authToken).then(function(data) {
							data ? element.css('display', 'initial') : element.css('display', 'none');
						});

					}

				});

			}
		};
	}]);