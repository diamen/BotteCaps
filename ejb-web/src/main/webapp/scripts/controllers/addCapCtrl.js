angular.module('bcControllers')
	.controller('addCapCtrl', function($scope, $stateParams, $uibModal, base64Service, restService) {

		$scope.country = $stateParams.country;

		$scope.beerOptions =
		[ {key: "Niepiwo", value: 0},
		  {key: "Piwo", value: 1} ];

		$scope.selected = [];

		$scope.clear = function() {
			$scope.files.length = 0;
		};

		$scope.uploadFiles = function(idx) {

			if(idx === undefined)
				idx = 0;

			var numberOfFiles = $scope.files.length;
			var i = idx;

			if($scope.selected[i] === undefined) {
				$scope.showAlert = true;
				return;
			}

			$scope.files[i].beer = $scope.selected[i].value;

			base64Service.imgToBase64($scope.files[i].src, 'image/jpeg', function(base64) {
				restService.adminController().imageUpload(base64, $scope.files[i].captext, $scope.files[i].capbrand, $scope.files[i].beer, $scope.country)
					.success(function(data) {
						if(i + 1 < numberOfFiles) {
							i += 1;
							$scope.uploadFiles(i);
						} else {
							$scope.$parent.redirectToCountry($scope.country);
						}
					});
			});
		};

		$scope.closeAlert = function() {
			$scope.showAlert = false;
		};
		
		/* modal */
		$scope.openModal = function(type) {
			
			if(type === 'CLR') {
				$scope.msg = "Czy chcesz wycofac zmiany?";
				$scope.invoke = $scope.clear;
			}

			if(type === 'UPL') {
				$scope.msg = "Czy chcesz dodac kapsle do kolekcji?";
				$scope.invoke = $scope.uploadFiles;
			}
			
			var modalInstance = $uibModal.open({
			      animation: true,
			      templateUrl: '/ejb-web/views/templates/modal.html',
			      controller: 'modalCtrl',
			      size: 'sm',
			      resolve: {
			        msg: function () {
			          return $scope.msg;
			        }
			      }
			    });
	
			modalInstance.result.then(function () {
			      $scope.invoke();
			    }, function () {
			      console.log('dismissed');
			    });
		};
		
});