angular.module('bcControllers')
	.controller('countriesCtrl', function($scope, restService) {

		restService.countriesController().getCountriesWithAmount().success(function(data) {
			$scope.countries = data;
		});

});