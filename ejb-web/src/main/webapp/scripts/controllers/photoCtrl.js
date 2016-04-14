angular.module('bcControllers')
	.controller('photoCtrl', function($scope, $window) {

		$scope.redirect = function() {
			$window.location.href = '/ejb-web/admin/login';
		}
		
	});