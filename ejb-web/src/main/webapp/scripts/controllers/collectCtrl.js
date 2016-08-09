angular.module('bcControllers')
	.controller('collectCtrl', function($scope, $stateParams, restService, base64Service, shareData) {
		
		var markedIds = [];
		$scope.country = $stateParams.country || 'Albania';
		$scope.orderCapsOptions = [{name: 'Alfabetycznie', value: 'cap_text'}, {name: 'Najstarsze', value: '-added_date'}, {name: 'Najnowsze', value: 'added_date'}];
		$scope.orderCaps = $scope.orderCapsOptions[0].value;
		
		restService.photoController().getCountryFlag($scope.country).success(function(data) {
			$scope.flag = data.flag;
		});
		
		if($scope.country === undefined)
			$scope.country = 'Albania';
		
		var convertPhotos = function(data) {
			var caps = [];
			
			for(var i = 0; i < data.length; i++) {
				var src = base64Service.base64ToUrl(data[i].base64);
				caps.push({src: src, id: data[i].id});
			}
			
			$scope.caps = caps;
		};
		
		restService.photoController().getImages($scope.country).success(function(data) {
			convertPhotos(data);
			shareData.addData($scope.caps);
		});
		
		$scope.filterCaps = function(searchText) {
			restService.photoController().getFilteredCaps($scope.country, searchText).success(function(data) {
				convertPhotos(data);
			});
		};
		
//		$scope.openCap = function(index) {
//			$location.path('/collect/' + $scope.country + '/' + $scope.caps[index].id);
//		};
		
		$scope.markCap = function(capId) {
			var index = markedIds.indexOf(capId);
			if(index > -1) {
				markedIds.splice(index, 1);
				console.log(markedIds);
				return;
			}
			markedIds.push(capId);
			
			console.log(markedIds);
		};
		
		$scope.deleteFiles = function() {
			
			var numberOfFiles = markedIds.length;
			var i = 0;
			var deleteFile = function(capId) {
				restService.adminController().imageDelete($scope.country, capId).success(function(data) {
					console.log(data);
					
					if(i + 1 < numberOfFiles) {
						i += 1;
						deleteFile(markedIds[i]);
					}
				});
			};
			
			deleteFile(markedIds[0]);
			
		};
		
//		$scope.addCapRedirect = function() {
//			$location.path('/admin/addcap/' + $scope.country);
//		};
		
	});