angular.module('bcControllers')
	.controller('photoCtrl', function($scope, $location) {

		$scope.redirect = function() {
			$location.path('/admin/login');
		}
		
	});