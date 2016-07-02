angular.module('bcControllers')
	.controller('capCtrl', function($scope, $routeParams, restService, base64Service) {
		
		$scope.country = $routeParams.country;
		$scope.id = $routeParams.id;

		restService.photoController().getSingleCap($scope.country, $scope.id).success(function(data) {
			$scope.cap = data;
			$scope.capsrc = base64Service.base64ToUrl(data.base64);
		});
		
});