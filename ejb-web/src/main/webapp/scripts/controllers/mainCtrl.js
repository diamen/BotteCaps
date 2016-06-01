angular.module('bcControllers', [])
	.controller('mainCtrl', function($scope, $location, $sessionStorage, $rootScope) {
		
		$rootScope.$storage = $sessionStorage;
		
		$scope.redirectCollect = function() {
			$location.path('/collect');
		}
		
	});