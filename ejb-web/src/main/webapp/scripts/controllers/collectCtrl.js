angular.module('bcControllers')
	.controller('collectCtrl', function($scope, $window, $state, $stateParams, $uibModal, restService, base64Service, markService, shareData) {

		$scope.markedIds = [];
		$scope.country = $stateParams.country || 'Albania';
		$scope.orderCapsOptions = [{name: 'Alfabetycznie', value: 'cap_text'}, {name: 'Najstarsze', value: '-added_date'}, {name: 'Najnowsze', value: 'added_date'}];
		$scope.orderCaps = $scope.orderCapsOptions[0].value;

		restService.countriesController().getFlag($scope.country).success(function(data) {
			$scope.flag = data.flag;
		});

		if($scope.country === undefined)
			$scope.country = 'Albania';

		$scope.convertPhotos = function(data) {
			var caps = [];

			for(var i = 0; i < data.length; i++) {
				var src = base64Service.base64ToUrl(data[i].base64);
				caps.push({src: src, id: data[i].id});
			}

			$scope.caps = caps;
		};

		restService.collectController().getCaps($scope.country).success(function(data) {
			$scope.convertPhotos(data);
			shareData.addData($scope.caps);
		});

		$scope.filterCaps = function(searchText) {
			restService.collectController().getFilteredCaps($scope.country, searchText).success(function(data) {
				$scope.convertPhotos(data);
			});
		};

		$scope.markCap = function(capId) {
			$scope.markedIds = markService($scope.markedIds, capId);
			console.log($scope.markedIds);
		};

		$scope.deleteFiles = function() {

			var numberOfFiles = $scope.markedIds.length;
			var i = 0;
			var deleteFile = function(capId) {
				restService.adminController().deleteCap(capId).success(function() {

					if(i + 1 < numberOfFiles) {
						i += 1;
						deleteFile($scope.markedIds[i]);
					} else {
						$window.location.reload();
					}
				});
			};

			deleteFile($scope.markedIds[0]);

		};

		/* modal */
		$scope.open = function() {

			var modalInstance = $uibModal.open({
			      animation: true,
			      templateUrl: '/ejb-web/views/templates/modal.html',
			      controller: 'modalCtrl',
			      size: 'sm',
			      resolve: {
			        msg: function () {
			          return "Czy chcesz usunac zaznaczone kapsle?";
			        }
			      }
			    });

			modalInstance.result.then(function () {
			      $scope.deleteFiles();
			    }, function () {
			      console.log('dismissed');
			    });
		};

	});