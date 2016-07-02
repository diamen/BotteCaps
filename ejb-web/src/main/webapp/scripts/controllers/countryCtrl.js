angular.module('bcControllers')
	.controller('countryCtrl', function($scope, $routeParams, $location, ngsrcConvertService, restService, base64Service) {
		
		$scope.country = $routeParams.country || 'Albania';
		$scope.orderCapsOptions = [{name: 'Alfabetycznie', value: 'cap_text'}, {name: 'Najstarsze', value: '-added_date'}, {name: 'Najnowsze', value: 'added_date'}];
		$scope.orderCaps = $scope.orderCapsOptions[0].value;
		
		restService.photoController().getCountryFlag($scope.country).success(function(data) {
			$scope.flag = data.flag;
		});
		
		if($scope.country === undefined)
			$scope.country = 'Albania';
		
		restService.photoController().getImages($scope.country).success(function(data) {
			var caps = [];
			
			for(var i = 0; i < data.length; i++) {
				var src = base64Service.base64ToUrl(data[i].base64);
				caps.push({src: src, id: data[i].id});
			}
			
			$scope.caps = caps;
		});
		
		$scope.filterCaps = function(searchText) {
			restService.photoController().getFilteredCaps(searchText).success(function(data) {
				for(var i = 0; i < data.length; i++) {
					data[i].cousrc = ngsrcConvertService.convert(data[i]);
				}
				$scope.couphotos = data;
			});
		};
		
		$scope.openCap = function(index) {
			$location.path('/collect/' + $scope.country + '/' + $scope.caps[index].id);
		};
		
		$scope.addCapRedirect = function() {
			$location.path('/admin/addcap/' + $scope.country);
		};
		
	});