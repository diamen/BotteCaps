angular.module('bcControllers')
	.controller('collectCtrl', function($scope, restService) {
		
		restService.photoController().getCountries().success(function(data) {
			$scope.countries = data;
		});
		
});