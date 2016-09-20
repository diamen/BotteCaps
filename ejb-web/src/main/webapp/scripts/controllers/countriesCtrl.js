angular.module('bcControllers')
	.controller('countriesCtrl', function($scope, $stateParams, $filter, restService) {

		restService.countriesController().getCountriesWithAmount().success(function(data) {
			$scope.countries = $filter('toPlCountry')(data);

			$scope.$parent.passData('countryAmountEvent', data);
		});

});