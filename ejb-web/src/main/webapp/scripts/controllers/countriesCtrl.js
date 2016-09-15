angular.module('bcControllers')
	.controller('countriesCtrl', function($scope, $stateParams, restService) {

		var country = $stateParams.country || 'Albania';

		restService.countriesController().getCountriesWithAmount().success(function(data) {
			$scope.countries = data;

			$scope.$parent.passData('countryAmountEvent', data);
		});

});