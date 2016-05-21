angular.module('bcControllers')
	.controller('collectCtrl', function($scope, $http, $location) {
		
		$http.get("./rest/collect/countries").success(function(data) {
			$scope.countries = data;
		});
		
		$scope.redirectToCountry = function(country) {
			$location.path('/collect/' + country);
		};
		
	});