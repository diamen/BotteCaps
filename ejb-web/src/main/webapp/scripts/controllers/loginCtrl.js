angular.module('bcControllers')
	.controller('loginCtrl', function($scope, $http, $sessionStorage, $rootScope, restService, authCacheFactory) {

		$http.get("./admin/login").success(function(data, status, headers, config, statusText) {
			$scope.csrfPreventionSalt = headers()['xsrf-token'];
		});

		$scope.login = function(user, password) {
			$http({
			    method: 'POST',
			    url: '/ejb-web/rest/auth/login/',
			    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			    transformResponse: function (data) {
			        return { data: data };
			    },
			    transformRequest: function(obj) {
			        var str = [];
			        for(var p in obj)
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        return str.join("&");
			    },
			    data: { username: user, password: password, csrfPreventionSalt: $scope.csrfPreventionSalt }
			}).success(function(data) {
				var token = angular.fromJson(data.data).token;
				$rootScope.$storage.authToken = token;
				$rootScope.$storage.loggedAs = user;
			}).error(function(data) {
				console.log(data);
			});
		};

		$scope.logout = function() {
			restService.authController().logout().success(function() {
				authCacheFactory.clearCache();
				
				$scope.uses = '';
				$scope.password = '';
				
				delete $rootScope.$storage.authToken;
				delete $rootScope.$storage.loggedAs;
			});
		};

	});