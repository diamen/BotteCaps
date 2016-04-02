angular.module('bcControllers', [])
	.controller('mainCtrl', function($scope, $location) {

		$scope.redirect = function() {
			$location.path('/login');
		};
		
	});