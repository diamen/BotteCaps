angular.module('bcControllers')
	.controller('sidebarCtrl', function($scope, restService, entityConverter) {

		restService.collectController().getNewestCaps().success(function(data) {
			$scope.data = data;
			$scope.caps = entityConverter(data);
		});

		$scope.openCap = function(index) {
			var countryId = $scope.data[index].entity.country_id;

			restService.countriesController().getCountry(countryId).success(function(country) {
				$scope.$parent.openCap(country, $scope.caps[index].id);
			});
		};

	});