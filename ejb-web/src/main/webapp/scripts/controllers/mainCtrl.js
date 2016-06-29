angular.module('bcControllers', [])
	.controller('mainCtrl', function($scope, $location, $sessionStorage, $rootScope) {
		
		$rootScope.$storage = $sessionStorage;
		
		// -------- Redirect Section --------
		$scope.redirect = function() {
			$location.path('/admin/login');
		};
		
		$scope.redirectCollect = function() {
			$location.path('/collect');
		};
		
		$scope.redirectToCountry = function(country) {
			$location.path('/collect/' + country);
		};
		
		$scope.openNews = function(id) {
			$location.path('/news/' + id);
		};
		
	});