angular.module('bcControllers')
	.controller('addCapCtrl', function($scope, $stateParams, modalService, base64Service, restService) {

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
				restService.adminController().addCap(base64, $scope.files[i].captext, $scope.files[i].capbrand, $scope.files[i].beer, $scope.country)
					.success(function() {
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

		$scope.openModalOk = function() {
			modalService.execute($scope.uploadFiles, "Czy chcesz dodać kapsle do kolekcji?");
		};

		$scope.openModalCancel = function() {
			modalService.execute(function() {
				$scope.files.length = 0;
			}, "Czy chcesz wycofać zmiany?");
		};

});