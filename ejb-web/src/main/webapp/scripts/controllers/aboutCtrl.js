angular.module('bcControllers')
	.controller('aboutCtrl', function($scope, $filter, restService) {

		restService.countriesController().getCountries().success(function(data) {
			$scope.countries = $filter('toPlCountry')(data);
		});

});