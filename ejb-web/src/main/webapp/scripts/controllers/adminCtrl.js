angular.module('bcControllers')
	.controller('adminCtrl', function($scope, restService) {

		restService.adminController().csrfPrevent().success(function() {
			$scope.csrfPreventionSalt = headers()['xsrf-token'];
		});

});