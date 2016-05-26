angular.module('bcControllers')
	.controller('loginCtrl', function($scope, $http) {
		
		$http.get("./admin/login").success(function(data, status, headers, config, statusText) {
			$scope.csrfPreventionSalt = headers()['xsrf-token'];
			console.log($scope.csrfPreventionSalt);
			
		});
		
	});