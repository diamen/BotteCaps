angular.module('bcControllers', [])
	.controller('mainCtrl', function($scope, $location) {
		
		$scope.redirectCollect = function() {
			$location.path('/collect');
		}
		
	});