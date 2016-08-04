angular.module('bcControllers', [])
	.controller('mainCtrl', function($scope, $state, $location, $sessionStorage, $rootScope) {
		
		$rootScope.$storage = $sessionStorage;
		
		/** Redirect */
		$scope.go = function(state) {
			$state.go(state);
		};
		
		// -------- Redirect Section --------
		$scope.redirect = function() {
			$location.path('/admin/login');
		};
		
		$scope.redirectMain = function() {
			$location.path('/');
		};
		
		$scope.redirectCollect = function() {
			$location.path('/collect');
		};
		
		$scope.redirectToCountry = function(country) {
			$location.path('/collect/' + country);
		};
		
	});