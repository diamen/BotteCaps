angular.module('bcControllers')
	.controller('countriesCtrl', function($scope, restService) {
		
		restService.photoController().getCountries().success(function(data) {
			$scope.countries = data;
		});
		
});