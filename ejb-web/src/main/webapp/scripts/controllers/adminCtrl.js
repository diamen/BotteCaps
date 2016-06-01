angular.module('bcControllers')
	.controller('adminCtrl', function($scope, $http, $rootScope) {
		
		$http.get("./admin/login").success(function(data, status, headers, config, statusText) {
			$scope.csrfPreventionSalt = headers()['xsrf-token'];
		});
		
		$scope.auth = function() {
			$http({
				method: "GET",
				url: "./rest/auth/secure/test",
				headers: {'AUTH-TOKEN': $rootScope.$storage.authToken }
				}).success(function(data) {
					console.log(data);
			});			
		};
		
	});