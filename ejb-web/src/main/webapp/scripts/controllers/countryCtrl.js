angular.module('bcControllers')
	.controller('countryCtrl', function($scope, $routeParams, $location, $http, ngsrcConvertService) {
		
		$scope.country = $routeParams.country;
		
		if($scope.country === undefined)
			$scope.country = 'Albania';
		
		$http.post("./rest/photo/bycountry/", $scope.country).success(function(data) {
			$scope.couphotos = data;
			
			var cousrcs = [];
			
			for(var i = 0; i < data.length; i++) {
				var capSrc = ngsrcConvertService.convert(data[i]);
				cousrcs.push(capSrc);
			}
			
			$scope.cousrcs = cousrcs;
		});
		
		$scope.openCap = function(index) {
			$location.path('/collect/' + $scope.country + '/' + $scope.couphotos[index].id);
		}
		
	});