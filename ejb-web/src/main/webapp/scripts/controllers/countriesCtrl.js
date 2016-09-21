angular.module('bcControllers')
	.controller('countriesCtrl', function($scope, $stateParams, $filter, restService, persistFactory) {

		restService.countriesController().getCountriesWithAmount().success(function(data) {
			$scope.countries = $filter('toPlCountry')(data);

			$scope.$parent.fire('countryAmountEvent', data);
			persistFactory.put('countryAmount', data);
		});

});