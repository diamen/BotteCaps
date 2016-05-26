angular.module('bcControllers')
	.controller('capCtrl', function($scope, $routeParams, $http, ngsrcConvertService) {
		
		$scope.country = $routeParams.country;
		$scope.id = $routeParams.id;

		$http.post("./rest/photo/idbycountry/", $scope.country).success(function(data) {
			$scope.countryId = data;
			
			$http.get("./rest/photo/singlecap/", 
			{ params: {"countryId" : $scope.countryId, "id" : $scope.id} } )
			.success(function(data) {
				$scope.cap = data;
				$scope.capsrc = ngsrcConvertService.convert(data);
			});
			
		});
		
});