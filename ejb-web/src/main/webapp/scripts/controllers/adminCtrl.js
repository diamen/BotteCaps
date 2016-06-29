angular.module('bcControllers')
	.controller('adminCtrl', function($scope, restService) {
		
		restService.adminController().csrfPrevent().success(function(data) {
			$scope.csrfPreventionSalt = headers()['xsrf-token'];
		});
	
});