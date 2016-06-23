angular.module('bcControllers')
	.controller('countryCtrl', function($scope, $routeParams, $location, $http, ngsrcConvertService, restService, base64Service) {
		
		$scope.country = $routeParams.country || 'Albania';
		
		$scope.orderCapsOptions = [{name: 'Alfabetycznie', value: 'cap_text'}, {name: 'Najstarsze', value: '-added_date'}, {name: 'Najnowsze', value: 'added_date'}];
		$scope.orderCaps = $scope.orderCapsOptions[0].value;
		
		$http.get("./rest/photo/flag/", { params: {"countryName" : $scope.country } } )
			.success(function(data) {
				$scope.flag = data.flag;
			});
		
		if($scope.country === undefined)
			$scope.country = 'Albania';
		
		$http.post("./rest/photo/bycountry/", $scope.country).success(function(data) {
			
			for(var i = 0; i < data.length; i++) {
				data[i].cousrc = ngsrcConvertService.convert(data[i]);
			}

			$scope.couphotos = data;
		});
		
		$scope.openCap = function(index) {
			$location.path('/collect/' + $scope.country + '/' + $scope.couphotos[index].id);
		};
		
		$scope.filterCaps = function(searchText) {
			$http.get("./rest/photo/filtercap/", 
					{ params: {"searchText" : searchText } } )
					.success(function(data) {
						for(var i = 0; i < data.length; i++) {
							data[i].cousrc = ngsrcConvertService.convert(data[i]);
						}
						$scope.couphotos = data;
				});
		};
		
		$scope.addCapRedirect = function() {
			$location.path('/admin/addcap/' + $scope.country);
		};
		
	});